//把普通字符串参数格式转换为json格式
//结果：{key:value,key:value...}
function conveterParamsToJson(paramsAndValues) { 
	//jsonObj: 创建一个json的空对象
    var jsonObj = {};  
  
    var param = paramsAndValues.split("&");  
    for ( var i = 0; param != null && i < param.length; i++) {  
        var para = param[i].split("=");  
        
        //给jsonObj添加属性和值
        //{name:'eric',minWeigth:10}
        jsonObj[para[0]] = para[1];  
    }  
    return jsonObj;  
}  
 
/**
 * 将表单数据封装为json
 * @param form
 * @returns
 */
function getFormData(form) {  
	//序列化表格的参数为字符串，格式：key=value&key=value...
    var formValues = $("#" + form).serialize(); 
    //decodeURIComponent: javascript的解决中文乱码问题
    return conveterParamsToJson(decodeURIComponent(formValues));  
}  