
    //与java插件代码交互的方法，由java插件实现，前端代码无需实现
    function cdfDownLoadByStr(value){

}
    function cdfDownLoad() {
    const cdfData = document.getElementById('cdfData').innerHTML;
    cdfDownLoadByStr(cdfData);
}

    //与java插件代码交互的方法，由java插件实现，前端代码无需实现
    function callBackJavaGetData() {
}

    //js执行java回调后，java在执行完成后，可以执行js代码；此代码在onsuccess中执行
    function setNewData(data) {
    alert(data);
    document.getElementById('cdfData').innerHTML = data;
}

    function refreshData(){
    //这种方式是正确的
    callBackJavaGetData();



}
