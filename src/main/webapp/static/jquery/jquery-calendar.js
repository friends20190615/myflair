(function($) {
	$.fn.extend({
		calendar : function(_setting){
			var $calendarEle = $(this),
			now = new Date(),
		    l = null,
		    uid = 'calendar_'+new Date().getTime()+Math.floor(Math.random()*1000+1),
			common_utils = {
				        _cache_: {},
				        VERSION: "1.0.2",
				        DKEY: "yyyy-mm",
				        MASK: "yyyy-mm-dd",
				        log: function(msg) {
				        	if(!common_utils._cache_.logs){
				        		common_utils._cache_.logs = [];
				        	}
				            if (!msg) return common_utils._cache_.logs;
				            common_utils._cache_.logs.push(msg);
				        },
				        pad: function(num, len) {
				        	num += "";
				            for (len = len || 2; num.length < len;){
				            	num = "0" + num;
				            }
				            return num;
				        },
				        format: function(dateTime,pattern) {
				        	pattern = pattern || this.MASK;
				            var date = dateTime.getDate();
				            var month = dateTime.getMonth(),
				            year = dateTime.getFullYear(),
				            hour = dateTime.getHours(),
				            minutes = dateTime.getMinutes(),
				            seconds = dateTime.getSeconds(),
				            time = dateTime.getTime(),
				            p = {
				            	d:date,
				                dd: common_utils.pad(date),
				                m: month + 1,
				                mm: common_utils.pad(month + 1),
				                yy: String(year).slice(2),
				                yyyy: year,
				                H: hour,
				                M: minutes,
				                S: seconds,
				                L: common_utils.pad(time, 3)
				            };
				            return pattern.replace((/d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|"[^"]*"|'[^']*'/g),
				            function(flag) {
				                return (flag in p ? p[flag] : flag.slice(1, flag.length - 1));
				            });
				        },
				        parseDateInt: function(date) {
				            "string" == typeof date && (date = date.toDate());
				            return (date ? parseInt(common_utils.format(date, "yyyymmdd"), 10) : false);
				        },
				        addMonth: function(date, value) {
				            return new Date(date.getFullYear(), date.getMonth() + value, 1);
				        },
				        get: function(date,firstWeek) {
				            if (!date) {
				            	common_utils.log("Date is null, Can't create calendar data structure.");
				            	return null;
				            }
				            var simple_date = common_utils.format(date, "yyyy-mm");
				            common_utils._cache_[simple_date] || common_utils.create(date,simple_date,firstWeek);
				            return common_utils._cache_[simple_date];
				        },
				        create: function(date, flag, firstWeek) {
				        	if(firstWeek > 6){
				        		firstWeek = 1;
				        	}
				            var year = date.getFullYear(),month = date.getMonth();
				            var week = (new Date(year, month, firstWeek)).getDay() - 1;
				            for (var g = [], l = 0; 42 > l; l++) {
				                var tempDate = new Date(year, month, l - week),
				                simpleDate = common_utils.format(tempDate, "yyyy-mm-dd"),
				                dateObj = {
				                    o: tempDate,
				                    k: simpleDate,
				                    i: parseInt(simpleDate.replace((/-/gi), ""), 10),
				                    y: tempDate.getFullYear(),
				                    m: tempDate.getMonth(),
				                    d: tempDate.getDate(),
				                    w: tempDate.getDay()
				                };
				                g.push(dateObj);
				            }
				            return common_utils._cache_[flag] = g;
				        }
				    };
			
			$.extend(true,String.prototype, {
				toDate: function() {
                    var rule = this.match(/^(\d{4})-(\d{1,2})-(\d{1,2})( \d{1,2}:\d{1,2}:\d{1,2}(\.\d+)?)?$/);
                    if (rule) {
                        var b = rule[1].toInt(),
                        c = rule[2].toInt() - 1,
                        rule = rule[3].toInt(),
                        d = new Date(b, c, rule);
                        if (d.getFullYear() == b && d.getMonth() == c && d.getDate() == rule) return d
                    }
                    return null
                },
                toInt: function() {
                	 return parseInt(this.replace((/,/g), ""), 10);
                },
                isDate: function() {
                    var a = this.match(/^(\d{4})-(\d{1,2})-(\d{1,2})$/);
                    if (a) {
                        var b = a[1].toInt(),
                        c = a[2].toInt() - 1,
                        a = a[3].toInt(),
                        d = new Date(b, c, a);
                        if (d.getFullYear() == b && d.getMonth() == c && d.getDate() == a) return true;
                    }
                    return false
                }
			});
		var setting = {
			    	options: {
			                container: null,
			                reference: false,
			                step: 2,	//单月、双月...
			                minDate: null, //最小日期
			                maxDate: null,	//最大日期
			                startDate: null,
			                endDate: null,
			                firstDayOfWeek: 1,	//自定义星期的第一天，默认日一二三四五六
			                permit: null,	//不可选日期添加例外  '2014-06-20'
			                prohibit: null,	//可选日期添加例外 '2014-06-20'
			                weekday: "0123456",	//控制日历星期是否可选
			                render: "default",	//
			                showAlways: false,		//控制日历框隐藏展示
			                showOptions: false,	//日历头年月下拉选择
			                showWeek: true,	//控制显示input文本框的星期提示图片
			                nextEl: null,	//鼠标焦点下一元素定位
			                rangeColor: "#D9E5F4",
			                defaultDate: null,
			                date: null,
			                tipText: "yyyy-mm-dd",
			                zindex: 9999,
			                location :'left'
			            },
			            string: {
			                header: "\u8bf7\u9009\u62e9\u65e5\u671f",
			                title: "yyyy\u5e74m\u6708",
			                week: "\u661f\u671f\u65e5 \u661f\u671f\u4e00 \u661f\u671f\u4e8c \u661f\u671f\u4e09 \u661f\u671f\u56db \u661f\u671f\u4e94 \u661f\u671f\u516d".split(" "),
			                // 对应日期由数组改成key value, edit by zhaohao
			                weekText:{
			                	0:"pic_sun",
			                	1:"pic_mon",
			                	2:"pic_tue",
			                	3:"pic_wed",
			                	4:"pic_thu",
			                	5:"pic_fir",
			                	6:"pic_sat"
			                },
			                todayText: ["pic_today", "pic_tomorrow", "pic_aftertomorrow"]
			            },
			            classNames: {
			                select: "day_selected",
			                nothismonth: "day_over",
			                blankdate: "day_no",
			                //修改今天的样式 edit by zhaohao
			                //today: "today",
			                tomorrow: "",
			                aftertomorrow: ""
			            },
			            listeners: {
			                onBeforeShow: null,
			                onShow: null,
			                onChange: null
			            },
			            template: {
			                wrapper: '<div class="calendar_wrap">\n<div class="calendar_content" id="'+uid+'_dayinfo"></div>\n<a class="month_prev" name="month_prev" href="javascript:void(0);" data-bind="prev"></a>\n<a class="month_next" name="month_next" href="javascript:void(0);" data-bind="next"></a>\n</div>',
			                // 新增星期顺序自定义功能，edit by zhaohao
			                calendar:function(){
			                	var headStr = '<div class="calendar_month${monthclass}">\n<div class="calendar_title">${title}</div>\n<dl class="calendar_day">\n'; 
			                	var tailStr = '\n<dd>\n{{each data}}\n{{if isfestival}}<a href="javascript:void(0);" ${classes}><span class="c_day_festival" ${attr}>${day}</span>{{else}}<a href="javascript:void(0);" ${classes} ${attr}>${day}{{/if}}</a>\n{{/each}}\n</dd>\n</dl>\n</div>';
			                	var arr = ['<dt>\u4e00</dt>','<dt>\u4e8c</dt>','<dt>\u4e09</dt>','<dt>\u56db</dt>','<dt>\u4e94</dt>','<dt class="weekend">\u516d</dt>','<dt class="weekend">\u65e5</dt>']; //一二三四五六日
			                	var midWeek='';
			                	var firstDayWeek = 1;
			                	if(_setting.options.firstDayOfWeek){
			                		firstDayWeek = _setting.options.firstDayOfWeek;
			                	}
			                	if(firstDayWeek==undefined|| firstDayWeek>6){
			                		firstDayWeek = 1;
			                	}
			            		if(firstDayWeek != 0){
			            			if(firstDayWeek == 1){
			            				midWeek = midWeek+arr[6]; 
			            			}else{
			            				for(var i=arr.length-firstDayWeek;i<arr.length;i++){
			                    			midWeek = midWeek+arr[i];
			                        	}
			            			}
			            		}
			                	for(var i=0;i<arr.length-firstDayWeek;i++){
			                		midWeek =midWeek+arr[i];
			                	}
			                	return headStr+midWeek+tailStr;
			                }(),
			                styles: "\n.calendar_wrap{position:relative;display:inline-block;font-size:12px;font-family:tahoma,\u200bArial,\u200bHelvetica,\u200bsimsun," +
			                		"\u200bsans-serif;border:2px solid #61a1df;border-right-width:1px;background:#fff;*display:inline;*zoom:1;box-shadow:0 3px 5px #ccc;}" +
			                		".calendar_content{background:#bbb;}.calendar_month{float:left;overflow:hidden;width:315px;border-right:1px solid #61a1df;}" +
			                		".calendar_title{height:33px;line-height:33px;font-size:14px;font-weight:bold;color:#61a1df;text-align:center;" +
			                		"background-color:#fff;border-bottom:1px solid #61a1df;}.month_next,.month_prev{position:absolute;top:7px;width:10px;height:17px;color:#fff;" +
			                		"background:url(http://image.zuchecdn.com/calendar/un_calender_index.png) no-repeat;cursor:pointer;cursor:pointer;}.month_prev{left:10px;}" +
			                		".month_next{right:10px;float:right;background-position:100% 0;}.month_prev:hover{background-position:0 -19px;}.month_next:hover{" +
			                		"background-position:-35px -19px;}.calendar_day{overflow:hidden;}.calendar_day dt{display:inline;float:left;width:40px;height:24px;line-height:24px;" +
			                		"padding-left:5px;color:#323232;}.calendar_day .weekend{color:#f90;}.calendar_day dd{_width:315px;}.calendar_day dd a{float:left;overflow:hidden;" +
			                		"width:39px;padding-left:4px;height:39px;line-height:22px;border-top:1px solid #e0e9f2;border-left:1px solid #e0e9f2;border-right:1px solid #fff;" +
			                		"border-bottom:1px solid #fff;font-size:12px;font-weight:bold;color:#323232;text-decoration:none;cursor:pointer;}.calendar_day .today,.calendar_day " +
			                		".c_festival_select,.calendar_day .c_festival_select:hover{background:#e6f4ff url(http://image.zuchecdn.com/calendar/un_calender_index.png) no-repeat;}" +
			                		".calendar_day a:hover{background-color:#fff1d6;border:1px solid #fdb811;text-decoration:none;}.calendar_day .today{background-color:#fff5d1;" +
			                		"background-position:0 -82px;}.calendar_day .day_over,.calendar_day .day_no{font-weight:normal;color:#b9b9b9;outline:none;cursor:default;}" +
			                		".calendar_day .day_over:hover,.calendar_day .day_no:hover{background:#fff;border-top:1px solid #e0e9f2;border-left:1px solid #e0e9f2;" +
			                		"border-right:1px solid #fff;border-bottom:1px solid #fff;}.calendar_day .day_selected,.calendar_day .day_selected:hover{background-color:#fff1d6;" +
			                		"border:1px solid #fdb811;}.calendar_day .c_festival_select,.calendar_day .c_festival_select:hover{background-color:#ffe6a6;" +
			                		"background-image:url(http://image.zuchecdn.com/calendar/un_calender_index.png);background-position:0 -111px;}.calendar_month.other{width:315px;}" +
			                		".calendar_day .c_festival_select,.calendar_day .c_festival_select:hover{background-color:#ffe6a6;background-image:url(http://image.zuchecdn.com/calendar/" +
			                		"un_calender_index.png);background-position:0 -111px;}.c_yuandan span,.c_chuxi span,.c_chunjie span,.c_yuanxiao span,.c_qingming span,.c_wuyi span," +
			                		".c_duanwu span,.c_zhongqiu span,.c_guoqing span,.c_jintian span,.c_shengdan span{width:39px;height:39px;background-image:url(http://image.zuchecdn.com" +
			                		"/calendar/un_festivals.png?v=1);background-repeat:no-repeat;text-indent:-9999em;overflow:hidden;display:block;}.c_yuandan.day_over span,.c_chuxi.day_over" +
			                		" span,.c_chunjie.day_over span,.c_yuanxiao.day_over span,.c_qingming.day_over span,.c_wuyi.day_over span,.c_duanwu.day_over span,.c_zhongqiu.day_over " +
			                		"span,.c_guoqing.day_over span,.c_jintian.day_over span,.c_shengdan.day_over span{width:39px;height:39px;background-image:url(http://image.zuchecdn.com/" +
			                		"calendar/un_festivalsgray.png?v=1);background-repeat:no-repeat;text-indent:-9999em;overflow:hidden;display:block;}.c_yuandan span{background-position:0 0;}" +
			                		".c_chuxi span{background-position:0 -40px;}.c_chunjie span{background-position:0 -80px;}.c_yuanxiao span{background-position:0 -120px;}" +
			                		".c_qingming span{background-position:0 -160px;}.c_wuyi span{background-position:0 -200px;}.c_duanwu span{background-position:0 -240px;}" +
			                		".c_zhongqiu span{background-position:0 -280px;}.c_guoqing span{background-position:0 -320px;}.c_jintian span{background-position:0 -360px;}" +
			                		".c_shengdan span{ background-position: 0 -400px;}.c_calender_date{display:inline-block;color:#666;text-align:right;position:absolute;z-index:1;}" +
			                		".calendar_wrap:before,.calendar_wrap:after{content:'.';display:block;overflow:hidden;visibility:hidden;font-size:0;line-height:0;width:0;height:0;}" +
			                		".calendar_wrap:after{clear:both;}\n"
			            },
			            festival: { //此处添加法定节假日
			            	"2018-02-15": ["c_chuxi", "\u9664\u5915"],
			            	"2017-01-27": ["c_chuxi", "\u9664\u5915"],
			            	"2016-02-07": ["c_chuxi", "\u9664\u5915"],
			                "2015-02-18": ["c_chuxi", "\u9664\u5915"],
			                "2014-01-30": ["c_chuxi", "\u9664\u5915"],
			                "2013-02-09": ["c_chuxi", "\u9664\u5915"],
			                "2018-02-16": ["c_chunjie", "\u6625\u8282"],
			                "2017-01-28": ["c_chunjie", "\u6625\u8282"],
			                "2016-02-08": ["c_chunjie", "\u6625\u8282"],
			                "2015-02-19": ["c_chunjie", "\u6625\u8282"],
			                "2014-01-31": ["c_chunjie", "\u6625\u8282"],
			                "2013-02-10": ["c_chunjie", "\u6625\u8282"],
			                "2018-03-02": ["c_yuanxiao", "\u5143\u5bb5"],
			                "2017-02-11": ["c_yuanxiao", "\u5143\u5bb5"],
			                "2016-02-22": ["c_yuanxiao", "\u5143\u5bb5"],
			                "2015-03-05": ["c_yuanxiao", "\u5143\u5bb5"],
			                "2014-02-14": ["c_yuanxiao", "\u5143\u5bb5"],
			                "2013-02-24": ["c_yuanxiao", "\u5143\u5bb5"],
			                "2018-04-05": ["c_qingming", "\u6e05\u660e"],
			                "2017-04-04": ["c_qingming", "\u6e05\u660e"],
			                "2016-04-04": ["c_qingming", "\u6e05\u660e"],
			                "2015-04-05": ["c_qingming", "\u6e05\u660e"],
			                "2014-04-05": ["c_qingming", "\u6e05\u660e"],
			                "2013-04-04": ["c_qingming", "\u6e05\u660e"],
			                "2018-06-18": ["c_duanwu", "\u7aef\u5348"],
			                "2017-05-30": ["c_duanwu", "\u7aef\u5348"],
			                "2016-06-09": ["c_duanwu", "\u7aef\u5348"],
			                "2015-06-20": ["c_duanwu", "\u7aef\u5348"],
			                "2014-06-02": ["c_duanwu", "\u7aef\u5348"],
			                "2013-06-12": ["c_duanwu", "\u7aef\u5348"],
			                "2018-09-24": ["c_zhongqiu", "\u4e2d\u79cb"],
			                "2017-10-04": ["c_zhongqiu", "\u4e2d\u79cb"],
			                "2016-09-15": ["c_zhongqiu", "\u4e2d\u79cb"],
			                "2015-09-27": ["c_zhongqiu", "\u4e2d\u79cb"],
			                "2014-09-08": ["c_zhongqiu", "\u4e2d\u79cb"],
			                "2013-09-19": ["c_zhongqiu", "\u4e2d\u79cb"],
			                "01-01": ["c_yuandan", "\u5143\u65e6"],
			                "05-01": ["c_wuyi", "\u52b3\u52a8"],
			                "10-01": ["c_guoqing", "\u56fd\u5e86"],
			                "12-25": ["c_shengdan", "\u5723\u8bde"]
			            }
			        };
				   var core = {
				        _init: function() {
				        	this._fragment = $(document.createDocumentFragment());
				        	this.container = $('<div class="calendar_content"></div>');
				        	$('body').append(this.container);
				        	this.container.css({'position':'absolute','top':0,'left':0,'width':0,'height':0,'z-index':999999});
				            this.step = 1 * (_setting.options.step || 1);
				            _setting.options.minDate && (_setting.options.minDate = _setting.options.minDate.toDate());
				            this.setMinDate(_setting.options.minDate);
				            _setting.options.maxDate && (_setting.options.maxDate = _setting.options.maxDate.toDate(), this._max = common_utils.parseDateInt(_setting.options.maxDate));
				            _setting.options.starDate && (this._start = common_utils.parseDateInt(_setting.options.startDate));
				            _setting.options.endDate && (this._end = common_utils.parseDateInt(_setting.options.endDate));
				            this._initTpl();
				        },
				        _initTpl: function() {
				            var template = _setting.template,
				            n = '<hr style="display:none;line-height:0;font-size:0;border:none;" /><style>' + template.styles.replace((/(\s*)([^\{\}]+)\{/g),
				            function(b, d, c) {
				                return d + c.replace((/([^,]+)/g), "#" + uid + " $1") + "{";
				            }) + "</style>",
				            $e = $(document.createElement("div"));
				            var offsetEle = $calendarEle;
				            var reltop = offsetEle.offset().top;
							var relleft = offsetEle.offset().left;
							var relheight = offsetEle.outerHeight();
							var relwidth = offsetEle.outerWidth();
							if(_setting.options.location == 'right'){
								relleft = relleft - 318 * _setting.options.step + relwidth;
							}
				            $e.attr("id",uid);
				            $e.css({left: relleft, top: reltop+relheight,'width': 318 * _setting.options.step + 'px','z-Index':_setting.options.zindex});
				            $e.html(n + template.wrapper);
				            this._layout = $e;
				            this._content = $("#"+uid+"_dayinfo");
				            var g = this,
				            f = true;
				            $e.mousedown(function(a){
				            	 var b = a.target || a.srcElement;
					             g._handleEvent(b);
					             f = false;
				            }).mouseup(function(){
				            	 f = true;
				            });
				            if(!_setting.options.showAlways){
				            	$(document).click(function(a){
				            		var e = $(a.target).closest('.calendar_content').hasClass('calendar_content');
				            		if(!e && a.target.id != $calendarEle.attr("id")){
				            			g.hide();
								        f = true;
				            		}
				            	});
				            };
				            $calendarEle.blur(function(){
				            	 f && g.hide();
				            })
				            if(this.reference){
				            	g._tempEnd = false;
				            	$e.mousemove(function(a){
				            		if(g.isIn(g._content, a.target)){
				            			g._checkHoverColor(a.target);
				            		}else if(g._tempEnd){
				            			g._tempEnd = false;
				            			g.update();
				            		}
				            	});
				            }
				        },
				        setMinDate: function(a) {
				            var b = false; (a ? b = common_utils.parseDateInt(a) : (_setting.options.minDate = now, b = l));
				            this._min = b;
				        },
				        isIn: function(a, b) {
				            for (var d = b; d && 9 !== d.nodeType;) {
				                if (d == a) return ! 0;
				                d = d.parentNode;
				            }
				            return false;
				        },
				        _handleEvent: function(a) {
				            var b = a.getAttribute("data-bind");
				            if (b) switch (b) {
				            case "close":
				                this.hide();
				                break;
				            case "prev":
				                this.changeDrawMonth( - this.step);
				                break;
				            case "next":
				                this.changeDrawMonth(this.step);
				                break;
				            case "select":
				                this.select(a);
				            }
				            return false;
				        },
				        changeDrawMonth: function(a) {
				            this._drawDate = common_utils.addMonth(this._drawDate, a);
				            this.update();
				        },
				        show: function() { //展示日历
				            if (!this._isShow) {
				                core.__inst && core.__inst.uid != this.uid && core.__inst.hide();
				                this._updateOptions();
				                var listeners = _setting.listeners;
				                listeners.onBerforeShow && listeners.onBerforeShow.call(this);
				                this.container.append(this._layout);
				                this.update();
				                _setting.options.showAlways || (this._layout.offset($calendarEle), core.__inst = this);
				                listeners.onShow && listeners.onShow.call(this);
				                this._isShow = true;
				                this.setPrevAndNext();
				            }
				        },
				        hide: function() {
				        	if(_setting.options.showAlways){
				        		return;
				        	}
				            this._isShow && (this._fragment.append(this._layout), this._isShow = false);
				        },
				        _updateOptions: function() {// 每次展示（show方法）日历时调用，作用是重新初始化日历参数
				        	var b = _setting.options;
				        	if(_setting.options.getMinDate 
			            			&& typeof(_setting.options.getMinDate) == 'function'){
			            		_setting.options.minDate = _setting.options.getMinDate().toDate();
			            		this.setMinDate(_setting.options.minDate);
			            	}
				            this.setCurrentDate($calendarEle);
				            this._start && this.currentDate && (b.endDate = this.currentDate, this._end = this.currentDint);
				            this._parseDrawMonth();
				        },
				        _parseDrawMonth: function() {
				            var a = false,
				            b = _setting.options; 
				            if(this.currentDate){
				            	this._drawDate = this.currentDate
				            }else if(b.defaultDate){
				            	a = b.defaultDate.toDate()
				            }else if(this._min){
				            	a = b.minDate;
				            	if(!a){
				            		a = now;
				            		this._drawDate = a;
				            	}else{
					            	this._drawDate = now;
					            }
				            }
				        },
				        setCurrentDate: function(a) {
				            a = a.val();
				            if(a.isDate()){
				            	a = a.toDate();
				            	if(!this.isPassDate(a)){
				            		this.currentDate = a;
				            		this.currentDint = common_utils.parseDateInt(a);
				            	}
				            }
				        },
				        update: function() { //渲染日历
				            var a = this.step,
				            b = 1,
				            d = '';
				            $("#"+uid+"_dayinfo").html('');
				            var currentMonth = this._create(this._drawDate);
				            if(currentMonth){
				            	$("#"+uid+"_dayinfo").append(currentMonth);
				            }
				            if (a > b){
				            	for (; b < a; b++){
				            		var otherMonth = this._create(common_utils.addMonth(this._drawDate, b), "other");
				            		if(otherMonth){
				            			$("#"+uid+"_dayinfo").append(otherMonth);
				            		}
				            	} 
				            }
				            if (_setting.options.showOptions) {
				                var c = this._layout.find("select"),
				                e = this;
				                c.bind("change",
				                function() {
				                    e._drawDate = new Date(c[0].value, 1 * c[1].value - 1, 1);
				                    e.update();
				                });
				            }
				            this.setPrevAndNext();
				        },
				        setPrevAndNext:function(){// 日历区间限制，edit by zhaohao
				        	var min=194901,max=205001;
				        	if(_setting.options.minDate != null){
				        		min = parseInt(common_utils.format(_setting.options.minDate, 'yyyymm'),10);
				        	}
				        	if(_setting.options.maxDate != null){
				        		max = parseInt(common_utils.format(_setting.options.maxDate, 'yyyymm'),10);
				        	}
				            var temMin = parseInt(common_utils.format(this._drawDate, 'yyyymm'),10);
				            var temMax = parseInt(common_utils.format(common_utils.addMonth(this._drawDate, 1), 'yyyymm'),10);
				            var prev = document.getElementsByName('month_prev');
				            var next = document.getElementsByName('month_next');
				            if(this.step==1){
				            	temMax = temMin;
				            }
				        	if(prev != null){
				        		for(var i=0;i<prev.length;i++){
					            	if(min>=temMin){
					            		prev[i].setAttribute('data-bind', '');
					            		$(prev[i]).hide();
					            	}else{
					            		prev[i].setAttribute('data-bind', 'prev');
					            		$(prev[i]).show();
					            	}
				        		}
				        	}
				        	if(next != null){
				        		for(var i=0;i<next.length;i++){
				        			if(max<=temMax){
				        				next[i].setAttribute('data-bind', '');
				        				$(next[i]).hide();
				        			}else{
				        				next[i].setAttribute('data-bind', 'next');
				        				$(next[i]).show();
				        			}
				        		}
				        	}
				        },
				        _create: function(a, b) {//创建日历
				            var d = _setting,
				            n = d.options,
				            e = a.getMonth(),
				            g = d.template,
				            l = "",
				            k = this._renderCalendar(a);
				            l = common_utils.format(a, d.string.title);
				            var $calendar_month = $('<div class="calendar_month"></div>');
				            if(b){
				            	$calendar_month.addClass(b);
				            }
				            $calendar_month.append($('<div class="calendar_title">' + l + '</div>'));
				            var $calendar_day = $('<dl class="calendar_day"></dl>');
				            $calendar_day.append('<dt class="weekend">日</dt><dt>一</dt><dt>二</dt><dt>三</dt><dt>四</dt><dt>五</dt><dt class="weekend">六</dt>');
				            var $calendar_day_dd = $('<dd></dd>');
				            if(k){
				            	$.each(k,function(i,data){
				            		var a = null;
				            		if(!data.attr){
				            			data.attr = '';
				            		}
				            		if(data.isfestival){
				            			a = $('<a href="javascript:void(0);" '+data.classes+'></a>');
				            			a.append($('<span class="c_day_festival" '+data.attr+'>'+data.day+'</span>'));
				            		}else{
				            			a = $('<a href="javascript:void(0);" '+data.classes+' '+data.attr+'>'+data.day+'</a>');
				            		}
				            		if(a){
				            			$calendar_day_dd.append(a);
				            		}
				            	})
				            	$calendar_day.append($calendar_day_dd);
				            	$calendar_month.append($calendar_day);
				            	return $calendar_month;
				            }
				        },
				        _renderCalendar: function(a) {
				            var b = common_utils.get(a,_setting.options.firstDayOfWeek),
				            d = a.getMonth(),
				            c = this;
				            a = 0;
				            for (var e = [], g = _setting.classNames, l = {
				                "default": function(a) {
				                    return (a.m == d ? c._parseCellDate(a, g, d) : {
				                        day: "",
				                        date: null,
				                        classes: ' class="' + g.blankdate + '"'
				                    });
				                },
				                all: function(a) {
				                    return c._parseCellDate(a, g, d);
				                }
				            } [_setting.options.render]; 42 > a; a++) e.push(l(b[a]));
				            return e;
				        },
				        _checkFestival: function(a) { //节假日检查
				            var b = a.substring(5),
				            d = _setting.festival;
				            // 新加今天的判断 edit by zhaohao
				            if(a==common_utils.format(new Date(), 'yyyy-mm-dd')){
				            	return ["c_jintian", "\u4eca\u5929"];
				            }
				            return (d[a] ? d[a] : (d[b] ? d[b] : !1));
				        },
				        _parseCellDate: function(a, b, d) {
				            var c = a.k, f = [];
				            d = a.i;
				            var e = a.w,
				            g = ' id="' + c + '"',
				            f = [];
				            a = {
				                day: a.d,
				                date: c,
				                classes: "",
				                attr: ""
				            };
				            d == l && f.push(b.today);
				            if (this._checkPassDate(c, d, e)){
				            	if (c = this._checkFestival(c)){
				            		a.isfestival = true;
				            		f.push(c[0]);
				            	}
				            	f.push(b.nothismonth);
				            }
				            else {
				                g += ' data-bind="select"';
				                if (c = this._checkFestival(c)) a.isfestival = true,
				                f.push(c[0]);
				                if(this.currentDate && this.currentDint == d){
				                	if(a.isfestival){
				                		f.push("c_festival_select");
				                	}else{
				                		f.push(b.select);
				                	}
				                }
				                this._start && (this._start == d && (a.isfestival ? f.push("c_festival_select") : f.push(b.select)), this._end && d > this._start && d < this._end && (g += ' style="background-color: ' + _setting.options.rangeColor + '"'), this._tempEnd && d > this._start && d < this._tempEnd && (g += ' style="background-color: ' + _setting.options.rangeColor + '"'));
				            }
				            0 < f.length && (a.classes = ' class="' + f.join(" ") + '"');
				            a.attr = g;
				            return a;
				        },
				        isPassDate: function(a) { //设置日期范围
				            var b = common_utils.format(a),
				            d = common_utils.parseDateInt(a);
				            a = a.getDay();
				            return this._checkPassDate(b, d, a);
				        },
				        _checkPassDate: function(a, b, d) { 
				            var c = _setting.options;
				            if(c.prohibit && -1 !== c.prohibit.indexOf(a)){
				            	return true;
				            }else if(c.permit && -1 !== c.permit.indexOf(a)){
				            	return false;
				            }else if(this._min && b < this._min || this._max && b > this._max || -1 === c.weekday.indexOf(d)){
				            	return true;
				            }else {
				            	return false;
				            }
				        },
				        _checkHoverColor: function(a) {
				            "select" == a.getAttribute("data-bind") && (a = a.id) && (a = common_utils.parseDateInt(a)) && a != this._tempEnd && a > this._start && (this._end ? a > this._end && (this._tempEnd = a, this.update()) : (this._tempEnd = a, this.update()));
				        },
				        select: function(a) { //选中日期方法
				            a = a.id;
				            var b = a.toDate(),
				            d = $calendarEle,
				            n = common_utils.format(b, _setting.options.tipText);
				            $calendarEle.val(a);
				            this.currentDate = b;
				            this.currentDint = common_utils.parseDateInt(b);
				            this.hide();
				            var e = this,
				            g = _setting.listeners;
				            g.onChange && setTimeout(function() {
				                g.onChange.call(e, d, n, false);
				            });
				            $calendarEle.trigger("change");
				            setTimeout(function() {
				                $(_setting.options.nextEl).focus();
				            });
				            _setting.options.showWeek && this.setWeek();
				            _setting.options.showAlways && this.update();
				        },
				        getWhatDay: function(a) { // 得到星期信息
				            var b = common_utils.format(a);
				            string = _setting.string;
				            if (b = this._checkFestival(b)) return b[0].replace("c_", "pic_");
				            b = new Date();
				            b = new Date(b.getFullYear(), b.getMonth(), b.getDate());
				            b = parseInt((a - b) / 864E5, 10);
				            return (0 <= b && 3 > b ? string.todayText[b] : string.weekText[a.getDay()]);
				        },
				        setWeek: function(a) {
				            var b = $calendarEle;
				            var a = b.val().toDate();
				            if(a){
				            	a = this.getWhatDay(a)
				            	if(105 <= b.offset().width){
				            		b.css({
						                "background-image": "url(http://image.zuchecdn.com/calendar/" + a + ".png)",
						                "background-position": "right center",
						                "background-repeat": "no-repeat"
						            })
				            	}
				            }else{
				            	b.css({
					                "background-image": "none"
					            })
				            }
				        }
				    };
				   _setting = $.extend(true,setting, _setting);
					if(_setting && _setting.options.date){
						var date = _setting.options.date.toDate();
						if(date){
							now = date;
						}
					}
					l = common_utils.parseDateInt(now);
					 var e = false;
					 if(_setting.options.showAlways){
						 if(!e){
							 core._init();
							 core.show();
							 e = true;
							 this._isShow = false;
						 }
					 }else{
						 $calendarEle.focus(function(){
							 if(!e){
								 core._init();
								 e = true;
							 }
							 core.show();
						 }).mousedown(function(){
							 if(!e){
								 core._init();
								 e = true;
							 }
							 core.show();
						 });
					 };
		}
	});
})(jQuery);