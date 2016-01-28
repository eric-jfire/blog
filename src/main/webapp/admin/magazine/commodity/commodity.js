function showCommodity(datagridId, showId)
{
    var path = $('#ctxPath').attr('path');
    var row = $('#' + datagridId).datagrid('getSelected');
    if (row) 
    {
        $.ajax(
        {
            url: path + '/CommodityAction/getIntroduce',
            dataType: 'json',
            data: 
            {
                id: row.id
            },
            success: function(msg)
            {
                $('#' + showId).empty();
                $('#' + showId).append(msg.data);
            }
            
        });
    }
    else 
    {
        $.messager.alert('通知', '要预览必须先选中一个商品');
    }
}

function addCommodity()
{
    addTab(
    {
        text: '增加商品',
        url: '/magazine/commodity/add.html'
    });
}

function editCommodity()
{
    if ($('#commodity_edit_dg').datagrid('getSelected') == null) 
    {
        $.messager.alert("通知", "请先选择一条记录");
        return;
    }
    var path = $('#ctxPath').attr('path');
    addTab(
    {
        text: '编辑商品',
        id: $('#commodity_edit_dg').datagrid('getSelected').id,
        url: '/magazine/commodity/add.html'
    });
}

function uploadCoverImg()
{
    var id = $('input[name=coverfile]').attr('id');
    var path = $('#ctxPath').attr('path');
    $.messager.progress(
    {
        text: '上传图片'
    });
    $.ajaxFileUpload(
    {
        url: path + '/CommodityAction/preShowCover',
        type: 'post',
        secureuri: false, // 一般设置为false
        fileElementId: id, // 上传文件的id、name属性名
        dataType: 'json', // 返回值类型，一般设置为json、application/json
        success: function(data, status)
        {
            var imgUrl = data.data;
            $('#cover').attr("src", imgUrl);
            $.messager.progress('close');
        },
        error: function(data, status, e)
        {
            alert(e);
        }
    });
}

function clearForm()
{
    $('#commodityadd-form').form('reset');
}

function submitForm()
{
    var path = $('#ctxPath').attr('path');
    $('#commodityadd-form').form('submit', 
    {
        url: path + '/CommodityAction/save',
        queryParams: 
        {
			title:$('#commodityadd-title').textbox('getValue'),
            introduce: UE.getEditor('commodityadd-introduce').getContent(),
            cover: $('#cover').attr("src")
        },
        success: function()
        {
            $.messager.show(
            {
                title: '通知',
                msg: '提交商品信息成功',
                timeout: 3000,
                showType: 'slide'
            });
            closeNowTab();
        }
    });
}

function initUeditor(id)
{
    var path = $('#ctxPath').attr('path');
    var ue = new baidu.editor.ui.Editor(
    {
        UEDITOR_HOME_URL: path + '/admin/base/ueditor/',
        serverUrl: path + '/ueditorConfigAction/jsConfig',
        toolbars: [['fullscreen', 'source', 'undo', 'redo', 'snapscreen', 'preview', 'simpleupload', 'insertimage', 'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc']],
        initialFrameHeight: '550',
        initialFrameWidth: '100%',
		autoHeightEnabled:false,
        enableAutoSave: false
    });
    ue.render('commodityadd-introduce');
    if (id) 
    {
        $("#magazine-commodity-add-mask").mask("载入商品数据中...");
        $.ajax(
        {
            url: getCommodityUrl,
            dataType: 'json',
            data: 
            {
                id: id
            },
            success: function(data)
            {
                $("#magazine-commodity-add-mask").unmask();
                if (data.success) 
                {
                    $('#commodityadd-form').form('load', data.data);
                    $('#cover').attr("src", data.data.cover);
					$('#commodityadd-title').textbox('setValue',data.data.title);
                    ue.ready(function()
                    {
                        if (data.data.introduce) 
                        {
                            ue.setContent(data.data.introduce);
                        }
                    });
                    $('#commodityadd-cover').change(function()
                    {
                        if ($(this).val() == null || $(this).val() == '') 
                        {
                            $('#cover').attr("src", data.data.cover);
                        }
                    });
                    $('#magazine-commodity-add-reset').click(function()
                    {
                        $('#cover').attr("src", data.data.cover);
                    });
                }
            }
        });
    }
}

function deleteCommodity()
{
    if ($('#commodity_edit_dg').datagrid('getSelected') == null) 
    {
        $.messager.alert("通知", "请先选择一条记录");
        return;
    }
    var checkedItems = $('#commodity_edit_dg').datagrid('getChecked');
    var names = [];
    $.each(checkedItems, function(index, item)
    {
        names.push(item.id);
    });
    $.ajax(
    {
        url: deleteCommoditysUrl,
        dataType: 'json',
        data: 
        {
            ids: names.join(',')
        },
        success: function()
        {
            $.messager.show(
            {
                title: '通知',
                msg: '商品删除成功',
                timeout: 3000,
                showType: 'slide'
            });
            $('#commodity_edit_dg').datagrid('load');
        }
    });
}

function commodityStateChange(state)
{
    if ($('#commodity_edit_dg').datagrid('getSelected') == null) 
    {
        $.messager.alert("通知", "请先选择一条记录");
        return;
    }
    var checkedItems = $('#commodity_edit_dg').datagrid('getChecked');
    var names = [];
    $.each(checkedItems, function(index, item)
    {
        names.push(item.id);
    });
    var message;
    if (state) 
    {
        message = "商品上架成功";
    }
    else 
    {
        message = "商品下架成功";
    }
    $.ajax(
    {
        url: commodityStateChangeUrl,
        dataType: 'json',
        data: 
        {
            ids: names.join(','),
            state: state
        },
        success: function()
        {
            $.messager.show(
            {
                title: '通知',
                msg: message,
                timeout: 3000,
                showType: 'slide'
            });
            $('#commodity_edit_dg').datagrid('load');
        }
    });
}
