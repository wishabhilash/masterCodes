package com.openni.test;
//
//import org.OpenNI.*;
//import com.googlecode.javacv.*;
//import com.googlecode.javacv.cpp.opencv_core.IplImage;
//
//
//public class Main
//{
//    
//    /**
//     * @param args
//     */
//    
//    Context context;
//    OutArg<ScriptNode> scriptNode;
//    
//    public static void main(String[] args)
//    {
//        // TODO Auto-generated method stub
//        Main main = new Main();
//        try{
//            main.init();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    
//    public void init() throws Exception{
////        scriptNode = new OutArg<ScriptNode>();
////        try{
////            context = Context.createFromXmlFile("data/SamplesConfig.xml", scriptNode);
////            context.openFileRecordingEx("data/Captured.oni");
////        }catch(GeneralException e){
////            e.printStackTrace();
////        }
//        CanvasFrame frame1 = new CanvasFrame("Video"); 
//        CanvasFrame frame2 = new CanvasFrame("Depth"); 
//        OpenKinectFrameGrabber grabber = new OpenKinectFrameGrabber(0); 
//        grabber.start(); 
//        IplImage video = grabber.grabVideo(); 
//        IplImage depth = grabber.grabDepth(); 
//        while (frame1.isVisible() && (video = grabber.grabVideo()) != null && (depth = grabber.grabDepth()) != null){
//            System.out.println("here");
//            frame1.showImage(video); 
//            frame2.showImage(depth); 
//        } 
//        
//    }
//    
//}


/****************************************************************************
*                                                                           *
*  OpenNI 1.x Alpha                                                         *
*  Copyright (C) 2011 PrimeSense Ltd.                                       *
*                                                                           *
*  This file is part of OpenNI.                                             *
*                                                                           *
*  OpenNI is free software: you can redistribute it and/or modify           *
*  it under the terms of the GNU Lesser General Public License as published *
*  by the Free Software Foundation, either version 3 of the License, or     *
*  (at your option) any later version.                                      *
*                                                                           *
*  OpenNI is distributed in the hope that it will be useful,                *
*  but WITHOUT ANY WARRANTY; without even the implied warranty of           *
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the             *
*  GNU Lesser General Public License for more details.                      *
*                                                                           *
*  You should have received a copy of the GNU Lesser General Public License *
*  along with OpenNI. If not, see <http://www.gnu.org/licenses/>.           *
*                                                                           *
****************************************************************************/

import org.OpenNI.*;

import java.nio.ShortBuffer;
import java.awt.*;
import java.awt.image.*;

class Main extends Component {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private OutArg<ScriptNode> scriptNode;
    private Context context;
    private DepthGenerator depthGen;
    private byte[] imgbytes;
    private float histogram[];

    private BufferedImage bimg;
    int width, height;

    private final String SAMPLE_XML_FILE = "data/SamplesConfig.xml";
    
    public static void main(String arg[]){
        Main main = new Main();
        
    }
    
    public Main() {

        try {
            scriptNode = new OutArg<ScriptNode>();
            context = Context.createFromXmlFile(SAMPLE_XML_FILE, scriptNode);

            depthGen = DepthGenerator.create(context);
            DepthMetaData depthMD = depthGen.getMetaData();

            histogram = new float[10000];
            width = depthMD.getFullXRes();
            height = depthMD.getFullYRes();
            
            imgbytes = new byte[width*height];
            
            DataBufferByte dataBuffer = new DataBufferByte(imgbytes, width*height);
            Raster raster = Raster.createPackedRaster(dataBuffer, width, height, 8, null);
            bimg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            bimg.setData(raster);

        } catch (GeneralException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private void calcHist(DepthMetaData depthMD)
    {
        // reset
        for (int i = 0; i < histogram.length; ++i)
            histogram[i] = 0;
        
        ShortBuffer depth = depthMD.getData().createShortBuffer();
        depth.rewind();

        int points = 0;
        while(depth.remaining() > 0)
        {
            short depthVal = depth.get();
            if (depthVal != 0)
            {
                histogram[depthVal]++;
                points++;
            }
        }
        
        for (int i = 1; i < histogram.length; i++)
        {
            histogram[i] += histogram[i-1];
        }

        if (points > 0)
        {
            for (int i = 1; i < histogram.length; i++)
            {
                histogram[i] = (int)(256 * (1.0f - (histogram[i] / (float)points)));
            }
        }
    }


    void updateDepth()
    {
        try {
            DepthMetaData depthMD = depthGen.getMetaData();

            context.waitAnyUpdateAll();
            
            calcHist(depthMD);
            ShortBuffer depth = depthMD.getData().createShortBuffer();
            depth.rewind();
            
            while(depth.remaining() > 0)
            {
                int pos = depth.position();
                short pixel = depth.get();
                imgbytes[pos] = (byte)histogram[pixel];
            }
        } catch (GeneralException e) {
            e.printStackTrace();
        }
    }


    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public void paint(Graphics g) {
        DataBufferByte dataBuffer = new DataBufferByte(imgbytes, width*height);
        Raster raster = Raster.createPackedRaster(dataBuffer, width, height, 8, null);
        bimg.setData(raster);

        g.drawImage(bimg, 0, 0, null);
    }
}
