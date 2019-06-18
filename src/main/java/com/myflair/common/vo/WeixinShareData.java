package com.myflair.common.vo;


/**
 * Created with IntelliJ IDEA.
 * User: GrayF(jy.feng@zuche.com))
 * Date: 2015/4/14
 * Time: 16:00
 */
public class WeixinShareData {

    private String url;

    private String shareTitle;

    private String shareImg;

    private String sharePyqImg;

    private String shareWXContent;

    private String sharePYQContent;
    
    private String shortUrl;

    private WeixinJSConfigVO weixinJSConfigVO;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }

    public String getShareWXContent() {
        return shareWXContent;
    }

    public void setShareWXContent(String shareWXContent) {
        this.shareWXContent = shareWXContent;
    }

    public String getSharePYQContent() {
        return sharePYQContent;
    }

    public void setSharePYQContent(String sharePYQContent) {
        this.sharePYQContent = sharePYQContent;
    }

    public WeixinJSConfigVO getWeixinJSConfigVO() {
        return weixinJSConfigVO;
    }

    public void setWeixinJSConfigVO(WeixinJSConfigVO weixinJSConfigVO) {
        this.weixinJSConfigVO = weixinJSConfigVO;
    }

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

    public String getSharePyqImg() {
        return sharePyqImg;
    }

    public void setSharePyqImg(String sharePyqImg) {
        this.sharePyqImg = sharePyqImg;
    }
}
