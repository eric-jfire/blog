$.extend($.fn.validatebox.defaults.rules, {
	passwordLength : {
		validator : function(value, param) {
			return $(param[0]).val().length > 6;
		},
		message : '密码的长度要大于6'
	}
});

$.extend($.fn.validatebox.defaults.rules, {
	btzero : {
		validator : function(value, param) {
			if (value > 0) {
				return true;
			} else {
				return false;
			}
		},
		message : '必须大于0'
	}
});

$.fn.tree.defaults.loadFilter = function(data, parent) {
	var opt = $(this).data().tree.options;
	var idFiled, textFiled, parentField;
	/* 如果parentField存在才会进行扁平化处理 */
	if (opt.parentField) {
		idFiled = opt.idFiled || 'id';
		textFiled = opt.textFiled || 'text';
		parentField = opt.parentField || 'pid';
		var i, l, treeData = [], tmpMap = [];
		for (i = 0, l = data.length; i < l; i++) {
			tmpMap[data[i][idFiled]] = data[i];
		}
		for (i = 0, l = data.length; i < l; i++) {
			if (tmpMap[data[i][parentField]]
					&& data[i][idFiled] != data[i][parentField]) {
				if (!tmpMap[data[i][parentField]]['children'])
					tmpMap[data[i][parentField]]['children'] = [];
				data[i]['text'] = data[i][textFiled];
				tmpMap[data[i][parentField]]['children'].push(data[i]);
			} else {
				data[i]['text'] = data[i][textFiled];
				treeData.push(data[i]);
			}
		}
		return treeData;
	}
	return data;
};
// 将表单序列化为json对象的方法
(function($) {
	$.fn.serializeJson = function() {
		var serializeObj = {};
		var array = this.serializeArray();
		var str = this.serialize();
		$(array).each(
				function() {
					if (serializeObj[this.name]) {
						if ($.isArray(serializeObj[this.name])) {
							serializeObj[this.name].push(this.value);
						} else {
							serializeObj[this.name] = [
									serializeObj[this.name], this.value ];
						}
					} else {
						serializeObj[this.name] = this.value;
					}
				});
		return serializeObj;
	};
})(jQuery);
//上传图像前的在本地预览效果
(function($) {
	jQuery.fn
			.extend({
				uploadPreview : function(opts) {
					opts = jQuery.extend({
						width : 0,
						height : 0,
						imgPreview : null,
						imgType : [ "gif", "jpeg", "jpg", "bmp", "png" ],
						callback : function() {
							return false;
						}
					}, opts || {});

					var _self = this;
					var _this = $(this);
					var imgPreview = $(opts.imgPreview);
					// 设置样式
					autoScaling = function() {
						imgPreview.css({
							"margin-left" : 0,
							"margin-top" : 0,
							"width" : opts.width,
							"height" : opts.height
						});
						imgPreview.show();
					}
					// file按钮出发事件
					_this
							.change(function() {
								if (this.value) {
									if (!RegExp(
											"\.(" + opts.imgType.join("|")
													+ ")$", "i").test(
											this.value.toLowerCase())) {
										alert("图片类型必须是"
												+ opts.imgType.join("，")
												+ "中的一种");
										this.value = "";
										return false;
									}
									if ($.browser.msie) {// 判断ie
										var path = $(this).val();
										if (/"\w\W"/.test(path)) {
											path = path.slice(1, -1);
										}
										imgPreview.attr("src", path);
										imgPreview.css({
											"margin-left" : 0,
											"margin-top" : 0,
											"width" : opts.width,
											"height" : opts.height
										});
										setTimeout("autoScaling()", 100);
									} else {
										if ($.browser.version < 7) {
											imgPreview.attr('src', this.files
													.item(0).getAsDataURL());
										} else {
													oFReader = new FileReader(),
													rFilter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;
											oFReader.onload = function(oFREvent) {
												imgPreview.attr('src',
														oFREvent.target.result);
											};
											var oFile = this.files[0];
											oFReader.readAsDataURL(oFile);
										}
										imgPreview.css({
											"margin-left" : 0,
											"margin-top" : 0,
											"width" : opts.width,
											"height" : opts.height
										});
										setTimeout("autoScaling()", 100);
									}
								}
								opts.callback();
							});
				}
			});
})(jQuery);