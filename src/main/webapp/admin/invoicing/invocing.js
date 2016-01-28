function initinvoicing_add_commodity_dg() {
	$('#invoicing-add-form').form({
		url : invoicingAddUrl,
		onSubmit : function() {
			return $('#invoicing-add-form').form('validate');
		},
		success : function() {
			$.messager.show({
				title : '通知',
				msg : '提交库存信息成功',
				timeout : 3000,
				showType : 'slide'
			});
		}
	});
	$('#invoicing-add-commodity-dg').datagrid({
		url : commodityListUrl,
		fit : true,
		checkOnSelect : false,
		selectOnCheck : false,
		singleSelect : true,
		pagination : true,
		fitColumns : true,
		idField : 'id',
		columns : [ [ {
			field : 'id',
			hidden : true,
			width : 20
		}, {
			field : 'name',
			title : '商品名称',
			width : 80
		}, {
			field : 'detail',
			title : '简要描述',
			width : 240
		}, {
			field : 'createtime',
			title : '创建时间',
			width : 120
		}, {
			field : 'updatetime',
			title : '更新时间',
			width : 120
		} ] ],
		onClickRow : function(index, row) {
			$('#invocing-add-name').textbox('setValue', row.name);
			$('#invocing-add-id').val(row.id);
		}
	});

	$('#invoicing-add-querybutton').click(function() {
		console.info($('#invoicing-add-query-start').datebox('getValue'));
		console.info($('#invoicing-add-query-end').datebox('getValue'));
		console.info($('#invoicing-add-query-content').textbox('getValue'));
		$("#invoicing-add-commodity-dg").datagrid('options').queryParams = {
			start : $('#invoicing-add-query-start').datebox('getValue'),
			end : $('#invoicing-add-query-end').datebox('getValue'),
			content : $('#invoicing-add-query-content').textbox('getValue')
		};
		$("#invoicing-add-commodity-dg").datagrid('load');
	});
	$('#invoicing-add-resetbutton').click(function() {
		$('#invoicing-add-query-start').datebox('reset');
		$('#invoicing-add-query-end').datebox('reset');
		$('#invoicing-add-query-content').textbox('reset')
	});
	$('#magazine-magazine-add-build').click(function() {
		$('#invoicing-add-form').form('submit');
	});
}
