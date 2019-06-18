// JavaScript Document
var mask_scroll = false; //是否可滚动
var mask_drag =  true;   //是否有遮罩
var mask_tops = 0;       //保存滚动条滚动时弹出窗口的top值
var eles_args = [];

(function($){
	$.maskLayer = function(obj){
		//初始化obj对象
		obj = {
			title  : obj.title || '',
			id     : obj.id,
			width  : obj.width || '',
			top    : obj.top || '',
			scroll : obj.scroll || 'false',
			mask   : obj.mask || 'true',
			drag   : obj.drag || 'false'
		};
		//先删除页面上已有的弹出层，避免无遮罩层的时候弹出多个
		$('#mask_layer_pane,#mask_layer_frame,#mask_pop_tips').remove();
		var mask_html = '<iframe id="mask_layer_frame" frameborder="0" style="display:none; position:absolute; width:100%; height:100%; left:0; top:0; z-index:1000; background:transparent; filter:alpha(opacity=0); opacity:0;"></iframe><div id="mask_layer_pane" class="mask_layer_pane"></div><div id="mask_pop_tips" class="mask_pop_tips" onmousemove="mask_move_(this,event)" onmousedown="mask_down_(this,event)" onmouseup="mask_up_(this)"><div class="mask_pop_title"><span id="mask_title" title="关闭">'+obj.title+'</span><a class="f-right clo_drag_btn" href="javascript:;" onclick="mask_close_();"> </a></div><div id="mask_add_s"></div></div>';
		$('body').append(mask_html);
		
		eles_args = [];
		eles_args.push($('#'+obj.id));
		eles_args.push($('#'+obj.id).parent());
		
		$('#mask_add_s').append($('#'+obj.id).show());
		
		var height = Math.max($('body').outerHeight(),$(window).height());
		$('#mask_layer_pane,#mask_layer_frame').css({height: height});
		$('#mask_layer_pane,#mask_layer_frame,#mask_pop_tips').show();
		//设置弹出框的左边位置
		if(obj.width != ''){
			$('#mask_pop_tips').css({width: obj.width,'margin-left': -obj.width/2});
		}
		//设置弹出框的上边位置
		if(obj.top == ''){
			var h = $('#mask_pop_tips').outerHeight();
			var winHeight = $(window).height();
			var scrollTop = $(document).scrollTop();
			if(h < winHeight){
				mask_tops =(winHeight-h)/2 + scrollTop;
				$('#mask_pop_tips').css({'top': mask_tops});
			}
			else{
				mask_tops = 0;
				$('#mask_pop_tips').css({top: scrollTop});
			}
		}
		else{
			$('#mask_pop_tips').css({top: obj.top});
		}
		//是否随滚动条滚动
		if(obj.scroll == 'true'){
			mask_scroll = true;
		}
		else{
			mask_scroll = false;
		}
		//是否遮罩
		if(obj.mask == 'false'){
			$('#mask_layer_pane,#mask_layer_frame').hide();
		}
		//是否可拖拽
		if(obj.drag == 'true'){
			mask_drag = true;
		}
		else{
			mask_drag = false;
		}
	}
})(jQuery);

var currentMoveObj = null;  //保存要拖拽的对象
var relLeft;  //相对左偏移
var relTop;   //相对上偏移
var mousePos; //鼠标的位置
//滚动条滚动事件
window.onscroll = function(){
	if(mask_scroll){
		var scroll_top = $(window).scrollTop();
		$('#mask_pop_tips').css({top: (mask_tops+scroll_top),'margin-top': 0});
	}
}
//关闭遮罩层
function mask_close_(){
	if(eles_args[1] != undefined){
		eles_args[1].append(eles_args[0].hide());
	}
	$('#mask_layer_pane,#mask_layer_frame,#mask_pop_tips').remove();
}
//鼠标按下
function mask_down_(eleObj,event){
	var flag1 = $(event.target).closest('.mask_pop_title').hasClass('mask_pop_title');
	var flag2 = $(event.srcElement).closest('.mask_pop_title').hasClass('mask_pop_title');
	
	if(mask_drag && (flag1 || flag2)){
		eleObj.style.cursor = 'move';
		currentMoveObj = eleObj;
		currentMoveObj.setCapture();
		mousePos = mousePosition(event);
		var objLeft = currentMoveObj.style.left;
		if(objLeft == ''){
			relLeft = mousePos.x - $(currentMoveObj).offset().left;
			relTop = mousePos.y - $(currentMoveObj).offset().top;
		}
		else{
			relLeft = mousePos.x - currentMoveObj.style.left.split('px')[0];
			relTop = mousePos.y - currentMoveObj.style.top.split('px')[0];
		}
	}
}
//鼠标按起
function mask_up_(eleObj){
	eleObj.style.cursor = 'default';
	if(currentMoveObj != null){
		currentMoveObj.releaseCapture();
		currentMoveObj = null;
	}
}
//拖动弹出层
function mask_move_(eleObj,event){
	if(currentMoveObj != null){
		mousePos = mousePosition(event);
		currentMoveObj.style.left = (mousePos.x - relLeft) + 'px';
		currentMoveObj.style.top = (mousePos.y - relTop) + 'px';
		currentMoveObj.style.marginTop = 0;
		currentMoveObj.style.marginLeft = 0;
	}
}
//获取鼠标位置
function mousePosition(ev){
	if(ev.pageX || ev.pageY){
		return {x:ev.pageX, y:ev.pageY};
	}
	return {x:ev.clientX + document.body.scrollLeft - document.body.clientLeft, y:ev.clientY + document.body.scrollTop - document.body.clientTop};
} 