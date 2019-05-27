$.extend($.fn.textbox.methods, {
	show: function(jq){
		return jq.each(function(){
			$(this).next().show();
		})
	},
	hide: function(jq){
		return jq.each(function(){
			$(this).next().hide();
		})
	},
	setRequired: function(jq, required) {
		return jq.each(function() {
			$(this).textbox('textbox').validatebox({required: required});
		});
	}
})
