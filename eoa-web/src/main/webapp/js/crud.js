//页面加载完成
		$(function(){
			//定义自定义验证器
			$.extend($.fn.validatebox.defaults.rules, {    
			    //minLength: 规则名称，给使用者调用的时候使用
			    minLength: {    
				//validator: 验证规则的逻辑
			        //value: 用户输入的内容
			        //param: 规则参数 (数组类型),  在使用者调用的时候传递过来的
			        validator: function(value, param){    
			            return value.length >= param[0];    
			        }, 
			        //message : 演示失败后提示信息（validator返回false的时候）
			        message: '最小为 {0}个字符.'   
			    }    
			});  

			
			//分页显示
			$("#list").datagrid({
				//url: 请求的url地址
				url:basePath+"/"+action+"/listByPage.action",
				//columns: 填充表的每列数据
				columns:columns,
				//分页查询
				pagination:true,
				//绑定工具条
				toolbar:"#toolbar"
			});
			
			//条件搜索
			$("#searchBtn").click(function(){
				//获取整个搜索表单的参数
				//serialize(): 序列化表单参数，为字符串格式
				//格式：name=10&minWeight=5&maxWeight=20
				//alert(decodeURIComponent($("#searchForm").serialize()));
				
				$("#list").datagrid("load",getFormData("searchForm"));
			});
			
			//点击添加，弹出录入窗口
			$("#addBtn").click(function(){
				//清空表单数据
				$("#editForm").form("clear");
				
				//打开窗口
				$("#editWin").window("open");
			});
			
			//保存数据
			$("#saveBtn").click(function(){
				//使用form组件的submit方法
				$("#editForm").form("submit",{
					//url:后台地址
					url:basePath+"/"+action+"/save.action",
					//onSubmit:表单提交前的回调函数， 返回true，提交表单；返回false，不提交表单
					onSubmit:function(){
						//验证表单校验是否全部通过，通过了，才可以提交表单
						return $("#editForm").form("validate");
					},
					success:function(data){ //data:服务器返回的数据  格式： {"success":true} 这是字符串类型
						//字符串类型转换为javascript的对象 : eval(字符串)： 返回js对象 ( data = eval("("+data+")");  )
						data = eval("("+data+")");
					
						if(data.success){
							
							//刷新datagrid
							$("#list").datagrid("reload");
							//关闭录入窗口
							$("#editWin").window("close");
							
							//成功
							$.messager.alert("提示","保存成功","info");
						}else{
							//失败
							$.messager.alert("提示","保存失败:"+data.msg,"error");
						}
					}
				});
			});
			
			//回显表单数据
			$("#editBtn").click(function(){
				//获取勾选的行
				var rows = $("#list").datagrid("getSelections");
				//只能选择一行
				if(rows.length!=1){
					$.messager.alert("提示","编辑操作只能选择一行","warning");
					return;
				}
				
				loadEditForm(rows[0]);
				
				//使用form的load方法，填充表单字段
				$("#editForm").form("load",basePath+"/"+action+"/findById.action?uuid="+rows[0].id);
				
				//打开录入窗口
				$("#editWin").window("open");
			});
			
			//批量删除
			$("#deleteBtn").click(function(){
				//判断至少选择一个
				var rows = $("#list").datagrid("getSelections");
				if(rows.length==0){
					$.messager.alert("提示","删除操作请至少选择一行","warning");
					return;
				}
				
				//确认提示是否删除
				$.messager.confirm("提示","确认删除数据吗？一旦删除不能恢复啦",function(value){
					if(value){
						
						//获取需要删除的id值    格式：1,2,3
						var ids = ""; 
						
						//遍历rows
						//each(): jQuery的遍历函数
						//设计一个数组来存储所有id
						var idArray = new Array();
						$(rows).each(function(i){
							idArray.push(rows[i].id); 
						});
						//join(字符): 使用某个字符来拼接数组里面的元素，最后组成字符串
						ids = idArray.join(",");
						
						//把ids参数传递到后台
						$.post(basePath+"/"+action+"/delete.action",{ids:ids},function(data){
							if(data.success){
								//刷新datagrid
								$("#list").datagrid("reload");
								
								$.messager.alert("提示","删除成功","info");
							}else{
								$.messager.alert("提示","删除失败："+data.msg,"error");
							}
						},"json");
						
					}
				});
			});
		});