package com.myflair.qrcode;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import com.google.zxing.common.BitMatrix;

public class MatrixToImageWriter {
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;
	// 二维码尺寸  
    private static final int QRCODE_SIZE = 400; 
	 // LOGO宽度  
    private static final int WIDTH = 40;  
    // LOGO高度  
    private static final int HEIGHT = 40;

	private MatrixToImageWriter() {
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	public static void writeToFile(BitMatrix matrix, String format, File file)
			throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format "
					+ format + " to " + file);
		}
	}

	public static void writeToStream(BitMatrix matrix, String format,
			OutputStream stream,String logoPath) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if(logoPath != null && logoPath.length() > 0){
			try {
				insertImage(image,logoPath,true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format "
					+ format);
		}
	}
	
	 /** 
     * 插入LOGO 
     *  
     * @param source 
     *            二维码图片 
     *
     * @param needCompress 
     *            是否压缩 
     * @throws Exception 
     */  
    private static void insertImage(BufferedImage source, String logoPath,  
            boolean needCompress) throws Exception {  
    /*    File file = new File(logoPath);  
        if (!file.exists()) {  
            System.err.println(""+logoPath+"   该文件不存在！");  
            return;  
        }  */
    	 URL url = null;  
         InputStream is = null;  
         Image src = null;
    	 url = new URL(logoPath);  
         is = url.openStream();  
         src = ImageIO.read(is); 
        int width = src.getWidth(null);  
        int height = src.getHeight(null);  
        if (needCompress) { // 压缩LOGO  
            if (width > WIDTH) {  
                width = WIDTH;  
            }  
            if (height > HEIGHT) {  
                height = HEIGHT;  
            }  
            Image image = src.getScaledInstance(width, height,  
                    Image.SCALE_SMOOTH);  
            BufferedImage tag = new BufferedImage(width, height,  
                    BufferedImage.TYPE_INT_RGB);  
            Graphics g = tag.getGraphics();  
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图  
            g.dispose();  
            src = image;  
        }  
        // 插入LOGO  
        Graphics2D graph = source.createGraphics();  
        int x = (QRCODE_SIZE - width) / 2;  
        int y = (QRCODE_SIZE - height) / 2;  
        graph.drawImage(src, x, y, width, height, null);  
        Shape shape = new RoundRectangle2D.Float(x, y, 0, 0, 6, 6);  
        graph.setStroke(new BasicStroke(3f));  
        graph.draw(shape);  
        graph.dispose();  
    }


}
