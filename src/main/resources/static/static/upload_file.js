var openid = ""
var uploading = false
var fileName = ""
var lastModified = 0
$(document).ready(function(){
	//提取openid
	(function ($) {
		$.getUrlParam = function (name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)")
			var r = window.location.search.substr(1).match(reg)
			if (r != null)
				return unescape(r[2])
			return null
		}
	})(jQuery)
	openId = $.getUrlParam("openId")

	//注册上传事件：发送文件并在成功后返回小程序
	$("#file").change(function(e) {
		if(uploading){
			alert("文件正在上传中，请稍候")
			return false
		}
		var file = $("#file")[0].files[0]
		var canUpload = false
		fileName = file.name
		lastModified = file.lastModified
		var formData = new FormData()
		formData.append('uploadFile', file)
		formData.append('openId', openId)
		if (file.size > 10 * 1024 * 1024){
			alert("现在只支持上传文件大小小于10Mb的文件，趣小印会在之后的升级逐步支持更大的文件，敬请期待..")
		}
		else{
			alert("【" + fileName + "】" )
			canUpload = true
		}
    // else if (fileName.split(".")[1] != "pdf"){
    //         alert("现在只支持上传pdf格式的文档，您可以通过使用手机版wps或者转pdf小程序来先将文件转成pdf格式，趣小印会在以后支持更多格式的文件，敬请期待..")
    //     }
		if (formData.values() != {} && canUpload){
			$.ajax({
				url: "http://www.finalexam.cn/print/upload",
				type: "POST",
				cache: false,
				data: formData,
				processData: false,
				contentType: false,
				beforeSend: function(){
					uploading = true
				},
				success: function(data) {
                    console.log(data)
					var url = ("/pages/printIndex/printIndex?fileName=" +
							fileName + "&lastModified=" + lastModified)
                    console.log(url)
					wx.miniProgram.navigateTo({
						url:url,
					})
				},
				error: function(e){
					console.log(e)
					alert("服务器繁忙，请稍后重试")
				},
				complete: function(e){
					uploading = false
					canUpload = false
				}
			})
		}
	})
})