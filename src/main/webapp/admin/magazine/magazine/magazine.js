function showMagazine(magazineId, commodityIds, showId)
{
    $.ajax(
    {
        url: showMagazineUrl,
        data: 
        {
            magazineId: magazineId,
            commodityIds: commodityIds
        },
        dataType: 'json',
        success: function(data)
        {
            $('#' + showId).empty();
            $('#' + showId).append(data.data);
        }
    });
}

function saveMagazine()
{
    var rows = $('#magazine-magazine-add-center-dg').datagrid('getRows');
    if (rows == null || rows.length == 0) 
    {
        $.messager.alert('通知', '请至少添加一个商品');
        return;
    }
    else 
    {
        var names = [];
        $.each(rows, function(index, item)
        {
            names.push(item.id);
        });
        $.ajax(
        {
            url: saveMagazineUrl,
            type: 'post',
            data: 
            {
                id: $('#magezine-magazine-add-id').val(),
                title: $('#magazine-magazine-add-title').textbox('getValue'),
                commodityIds: names.join(','),
                current: $('#magazine-magazine-add-current').combobox('getValue')
            },
            dataType: 'json',
            success: function()
            {
                $.messager.show(
                {
                    title: '通知',
                    msg: '生成杂志成功',
                    timeout: 3000,
                    showType: 'slide'
                });
                closeNowTab();
            }
        });
    }
}

function addMagazine()
{
    addTab(
    {
        text: '增加杂志',
        url: 'magazine/magazine/add.html'
    });
}

function editMagazine(id)
{
    addTab(
    {
        text: '编辑杂志',
        url: 'magazine/magazine/add.html',
        id: id
    });
}

function buildMagazine(maskId)
{
    var table = $('#magazine-magazine-list-dg');
    var rows = table.datagrid('getChecked');
    if (rows == null || rows.length == 0) 
    {
        $.messager.alert('通知', '请至少选择一个杂志');
        return;
    }
    else 
    {
        var ids = [];
        $.each(rows, function(index, row)
        {
            ids.push(row.id);
        });
        ids = ids.join(',');
        $('#' + maskId).mask('杂志生成中...')
        $.ajax(
        {
            url: buildMagazineUrl,
            dataType: 'json',
            data: 
            {
                ids: ids
            },
            success: function(data)
            {
                if (data.success) 
                {
                    $.messager.show(
                    {
                        title: '通知',
                        msg: '生成杂志成功',
                        timeout: 3000,
                        showType: 'slide'
                    });
                }
                $('#' + maskId).unmask();
            }
        });
    }
}

function deleteMagazine()
{
    var table = $('#magazine-magazine-list-dg');
    var rows = table.datagrid('getChecked');
    if (rows == null || rows.length == 0) 
    {
        $.messager.alert('通知', '请至少选择一个杂志');
        return;
    }
    else 
    {
        var ids = [];
        $.each(rows, function(index, row)
        {
            ids.push(row.id);
        });
        $.ajax(
        {
            url: deleteMagazineUrl,
            data: 
            {
                ids: ids.join(',')
            },
            dataType: 'json',
            success: function()
            {
                $.messager.show(
                {
                    title: '通知',
                    msg: '删除杂志成功',
                    timeout: 3000,
                    showType: 'slide'
                });
                table.datagrid('load');
                table.datagrid('clearSelections');
            }
        });
    }
    
}

function addCommodityToMagazine()
{
    var rows = $('#magazine-magazine-add-commodity-dg').datagrid('getChecked');
    if (rows == null || rows.length == 0) 
    {
        $.messager.alert('通知', '请至少选择一个商品');
    }
    else 
    {
        var datagrid = $('#magazine-magazine-add-center-dg');
        $.each(rows, function(index, row)
        {
            datagrid.datagrid('appendRow', row);
        });
        var length = datagrid.datagrid('getRows').length;
        for (var i = 0; i < length; i++) 
        {
            datagrid.datagrid('refreshRow', i);
        }
        $.parser.parse('#magazine-magazine-add-center-dg');
        $('#magazine-magazine-add-commoditywindow').window('close');
        
    }
}

//在新建杂志的列表中上移商品
function magezineUpCommodity(index)
{
    var gridid = "magazine-magazine-add-center-dg";
    magazineSortCommodity(index, "up", gridid);
}

//在新建杂志的列表中下移商品
function magezineDownCommodity(index)
{
    var gridid = "magazine-magazine-add-center-dg";
    magazineSortCommodity(index, "down", gridid);
}

function magazineSortCommodity(index, type, gridid)
{
    if ("up" == type) 
    {
        if (index != 0) 
        {
            var toup = $('#' + gridid).datagrid('getData').rows[index];
            var todown = $('#' + gridid).datagrid('getData').rows[index - 1];
            $('#' + gridid).datagrid('getData').rows[index] = todown;
            $('#' + gridid).datagrid('getData').rows[index - 1] = toup;
            $('#' + gridid).datagrid('refreshRow', index);
            $('#' + gridid).datagrid('refreshRow', index - 1);
            $('#' + gridid).datagrid('selectRow', index - 1);
        }
    }
    else 
    {
        if ("down" == type) 
        {
            var todown = $('#' + gridid).datagrid('getData').rows[index];
            var toup = $('#' + gridid).datagrid('getData').rows[index + 1];
            $('#' + gridid).datagrid('getData').rows[index + 1] = todown;
            $('#' + gridid).datagrid('getData').rows[index] = toup;
            $('#' + gridid).datagrid('refreshRow', index);
            $('#' + gridid).datagrid('refreshRow', index + 1);
            $('#' + gridid).datagrid('selectRow', index + 1);
        }
    }
    $.parser.parse('#magazine-magazine-add-center-dg');
}

//初始化添加商品到杂志的窗口中的数据表格
function initMagazine_magazine_add_commoditywindow()
{
    $('#magazine-magazine-add-commodity-dg').datagrid(
    {
        url: commodityListUrl,
        fit: true,
        checkOnSelect: false,
        selectOnCheck: false,
        singleSelect: true,
        pagination: true,
        fitColumns: true,
        idField: 'id',
        columns: [[
        {
            field: 'id',
            checkbox: true,
            width: 20
        }, 
        {
            field: 'name',
            title: '商品名称',
            width: 80
        }, 
        {
            field: 'detail',
            title: '简要描述',
            width: 240
        }, 
        {
            field: 'createtime',
            title: '创建时间',
            width: 120
        }, 
        {
            field: 'updatetime',
            title: '更新时间',
            width: 120
        }]],
        toolbar: [
        {
            iconCls: 'icon-edit',
            text: '预览',
            handler: function()
            {
                showCommodity('magazine-magazine-add-commodity-dg', 'magazine-magazine-add-commodity-show');
            }
        }, '-', 
        {
            iconCls: 'icon-add',
            text: '添加到杂志',
            handler: function()
            {
                addCommodityToMagazine();
            }
        }]
    });
    
    $('#magazine-magazine-selectCommodity-querybutton').click(function()
    {
        $("#magazine-magazine-add-commodity-dg").datagrid('options').queryParams = 
        {
            start: $('#magazine-magazine-selectCommodity-query-start').datebox('getValue'),
            end: $('#magazine-magazine-selectCommodity-query-end').datebox('getValue'),
            content: $('#magazine-magazine-selectCommodity-query-content').textbox('getValue')
        };
        $("#magazine-magazine-add-commodity-dg").datagrid('load');
    });
    $('#magazine-magazine-add-resetbutton').click(function()
    {
        $('#magazine-magazine-selectCommodity-query-start').datebox('reset');
        $('#magazine-magazine-selectCommodity-query-end').datebox('reset');
        $('#magazine-magazine-selectCommodity-query-content').textbox('reset')
    });
}
