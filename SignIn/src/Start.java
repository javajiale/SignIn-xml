import cn.zucc.edu.signin.ui.FrmMain;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.skin.EmeraldDuskSkin;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

/**

 * Created by blue on 16-6-1.
 */


public class Start {
    @SuppressWarnings("unchecked")
    public static void initGlobalFontSetting(Font fnt) {
        FontUIResource fontRes = new FontUIResource(fnt);
        for (Enumeration keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
                UIManager.put(key, fontRes);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel( new org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel());
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
           // SubstanceLookAndFeel.setCurrentTheme(new SubstanceTerracottaTheme());
        //  SubstanceLookAndFeel.setSkin(new EmeraldDuskSkin());
          SubstanceLookAndFeel.setCurrentButtonShaper(new ClassicButtonShaper());
        //  SubstanceLookAndFeel.setCurrentWatermark(new SubstanceBubblesWatermark());
      //    SubstanceLookAndFeel.setCurrentBorderPainter(new StandardBorderPainter());
     //       SubstanceLookAndFeel.setCurrentGradientPainter(new StandardGradientPainter());
     //       SubstanceLookAndFeel.setCurrentTitlePainter(new FlatTitePainter());
        } catch (Exception e) {
            System.err.println("Something went wrong!");
        }
        initGlobalFontSetting(new Font("黑体",Font.PLAIN,20));
        new FrmMain();

    }


}
