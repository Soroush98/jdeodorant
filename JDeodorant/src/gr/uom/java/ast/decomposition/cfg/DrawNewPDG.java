package gr.uom.java.ast.decomposition.cfg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.*;


public class DrawNewPDG extends JPanel {
   private  static PDG pdg;
   private  static CFG cfg;
   private static  int RECT_X = 400;
   private static  int RECT_Y = 20;
   private static final int RECT_WIDTH = 20;
   private static final int RECT_HEIGHT = RECT_WIDTH;

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Set <GraphEdge> ed  = pdg.edges;
      	for (GraphEdge edge : ed) {
		  
    	  GraphNode src = edge.getSrc();
    	  GraphNode dst = edge.getDst();
    	  System.out.print(src.id + " -->" + dst.id);
    	  g.drawRect(RECT_X, RECT_Y, RECT_WIDTH, RECT_HEIGHT);
    	  int muls = 25*( src.getId()%2 -1 ) *src.getId() ;
    	  int muld = 25*( dst.getId()%2 -1 ) *dst.getId();
    	  PDGDependence dependence = (PDGDependence)edge;
    	  if (src.id != 0 && dst.id != 0) {
    	  if (src.getId() < dst.getId()) {
    		  
    		  if (dependence instanceof PDGControlDependence)
    			  drawArrowLine(g,RECT_X + muls,RECT_Y+(50*(src.getId()-1)) + RECT_HEIGHT,RECT_X+muld,RECT_Y+(50*(dst.getId()-1)),5,5,0);
    		  if (dependence instanceof PDGDataDependence)
    			  drawArrowLine(g,RECT_X + muls,RECT_Y+(50*(src.getId()-1)) + RECT_HEIGHT,RECT_X+muld,RECT_Y+(50*(dst.getId()-1)),5,5,1);
    		
    	  }
    	  else {
    		  if (dependence instanceof PDGControlDependence)
        	  drawArrowLine(g,RECT_X+muls + 10,RECT_Y+(50*(src.getId()-1)) -20 + RECT_HEIGHT,RECT_X + muld+10,RECT_Y+(50*(dst.getId()-1))+20,5,5,0);
    		  if (dependence instanceof PDGDataDependence)
            	  drawArrowLine(g,RECT_X+muls + 10,RECT_Y+(50*(src.getId()-1)) -20 + RECT_HEIGHT,RECT_X + muld+10,RECT_Y+(50*(dst.getId()-1))+20,5,5,1);
    	
  
    	  }
    	  }
      
		}
      Set <GraphNode> nd  = cfg.nodes;
      Iterator iter = nd.iterator();
		while (iter.hasNext()) {
			  GraphNode first = (GraphNode)iter.next();
	    	  String nodes = (Integer.toString(first.getId()+1));
	    	  int mul = 25*( first.getId()%2 -1  ) *first.getId();
	    	  g.drawRect(RECT_X + mul , RECT_Y, RECT_WIDTH, RECT_HEIGHT);
	    	  g.drawString(nodes , (RECT_X + mul+RECT_WIDTH/2), (RECT_Y+RECT_HEIGHT/2));
	    	  RECT_Y = RECT_Y +  50;
			}
   
     
   }
   public static void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h, int color) {
	    int dx = x2 - x1, dy = y2 - y1;
	    double D = Math.sqrt(dx*dx + dy*dy);
	    double xm = D - d, xn = xm, ym = h, yn = -h, x;
	    double sin = dy / D, cos = dx / D;

	    x = xm*cos - ym*sin + x1;
	    ym = xm*sin + ym*cos + y1;
	    xm = x;

	    x = xn*cos - yn*sin + x1;
	    yn = xn*sin + yn*cos + y1;
	    xn = x;

	    int[] xpoints = {x2, (int) xm, (int) xn};
	    int[] ypoints = {y2, (int) ym, (int) yn};
	    if (color == 0)
			   g.setColor(Color.BLUE);
		   else 
			   g.setColor(Color.RED);
	
	    g.drawLine(x1, y1, x2, y2);
	    g.fillPolygon(xpoints, ypoints, 3);
	}
   @Override
   public Dimension getPreferredSize() {
      // so that our GUI is big enough
      return new Dimension(1000, 700);
   }

   // create the GUI explicitly on the Swing event thread
   public static void createAndShowGui(PDG pdg,CFG cfg) {
      DrawNewPDG mainPanel = new DrawNewPDG();
      mainPanel.pdg = pdg;
      mainPanel.cfg = cfg;
      JFrame frame = new JFrame("PDG");
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.getContentPane().add(mainPanel);
      frame.pack();
      frame.setLocationByPlatform(true);
      frame.setVisible(true);
   }

   
}