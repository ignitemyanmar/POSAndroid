// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package EasyTicketBrowser.src.detailed.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.cef.OS;
import org.cef.browser.CefBrowser;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {

  private final JButton backButton_;
  private final JButton forwardButton_;
  private final JButton reloadButton_;
  private final JTextField address_field_;
  private final JLabel zoom_label_;
  private double zoomLevel_ = 0;
  private final CefBrowser browser_;
  private Font font;
  private final Dimension d;
  private int radius;
  
  public ControlPanel(CefBrowser browser) {
    browser_ = browser;
    setEnabled(browser_ != null);

    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

    add(Box.createHorizontalStrut(5));
    add(Box.createVerticalStrut(5));

    font = new Font("Verdana", Font.PLAIN, 14);
    //set button size
    d = new Dimension(90,30);
    radius = 5;
    
    backButton_ = new JButton("Back");
    backButton_.setFont(font);
    backButton_.setAlignmentX(LEFT_ALIGNMENT);    
    //backButton_.setBorder(new RoundedBorder(radius));
    backButton_.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        browser_.goBack();
      }
    });
    add(backButton_);
    add(Box.createHorizontalStrut(5));

    forwardButton_ = new JButton("Forward");
    forwardButton_.setFont(font);
    forwardButton_.setAlignmentX(LEFT_ALIGNMENT);
    //forwardButton_.setBorder(new RoundedBorder(radius));
    forwardButton_.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        browser_.goForward();
      }
    });
    add(forwardButton_);
    add(Box.createHorizontalStrut(5));

    reloadButton_ = new JButton("Reload");
    reloadButton_.setFont(font);
    reloadButton_.setPreferredSize(d);
    
    //reloadButton_.setBorder(new RoundedBorder(radius));
    reloadButton_.setAlignmentX(LEFT_ALIGNMENT);
    reloadButton_.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (reloadButton_.getText().equalsIgnoreCase("reload")) {
          int mask = OS.isMacintosh()
                     ? ActionEvent.META_MASK
                     : ActionEvent.CTRL_MASK;
          if ((e.getModifiers() & mask) != 0) {
            System.out.println("Reloading - ignoring cached values");
            browser_.reloadIgnoreCache();
          } else {
            System.out.println("Reloading - using cached values, "+e.getModifiers()+", "+mask);
            browser_.reload();
          }
        } else {
          browser_.stopLoad();
        }
      }
    });
    add(reloadButton_);
    add(Box.createHorizontalStrut(5));

    JLabel addressLabel = new JLabel("Address:");
    addressLabel.setAlignmentX(LEFT_ALIGNMENT);
    add(addressLabel);
    add(Box.createHorizontalStrut(5));

    address_field_ = new JTextField(100);
    font = new Font("Verdana", Font.PLAIN, 13);
    address_field_.setFont(font);
    address_field_.setAlignmentX(LEFT_ALIGNMENT);
    address_field_.disable();
    //address_field_.setBorder(new RoundedBorder(radius));
    address_field_.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        browser_.loadURL(getAddress());
      }
    });
    address_field_.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent arg0) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
            .clearGlobalFocusOwner();
        address_field_.requestFocus();
      }
    });
    add(address_field_);
    add(Box.createHorizontalStrut(5));

    JButton goButton = new JButton("Go");
    goButton.setFont(font);
    goButton.setAlignmentX(LEFT_ALIGNMENT);
    //goButton.setBorder(new RoundedBorder(radius));
    goButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        browser_.loadURL(getAddress());
      }
    });
    add(goButton);
    add(Box.createHorizontalStrut(5));

    JButton minusButton = new JButton("-");
    minusButton.setFont(font);
    minusButton.setAlignmentX(CENTER_ALIGNMENT);
   // minusButton.setBorder(new RoundedBorder(radius));
    minusButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        browser_.setZoomLevel(--zoomLevel_);
        zoom_label_.setText(new Double(zoomLevel_).toString());
      }
    });
    add(minusButton);

    zoom_label_ = new JLabel("0.0");
    add(zoom_label_);

    JButton plusButton = new JButton("+");
    plusButton.setFont(font);
    plusButton.setAlignmentX(CENTER_ALIGNMENT);
    //plusButton.setBorder(new RoundedBorder(radius));
    plusButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        browser_.setZoomLevel(++zoomLevel_);
        zoom_label_.setText(new Double(zoomLevel_).toString());
      }
    });
    add(plusButton);
  }

  public void update(CefBrowser browser, boolean isLoading, boolean canGoBack, boolean canGoForward) {
    if (browser == browser_) {
      backButton_.setEnabled(canGoBack);
      forwardButton_.setEnabled(canGoForward);
      reloadButton_.setText( isLoading ? "X" : "Reload");
      reloadButton_.setFont(font);
      //reloadButton_.setForeground(Color.RED);
    }
  }

  public String getAddress() {
    String address = address_field_.getText();
    // If the URI format is unknown "new URI" will throw an
    // exception. In this case we interpret the value of the
    // address field as search request. Therefore we simply add
    // the "search" scheme.
    try {
      address = address.replaceAll(" ", "%20");
      URI test = new URI(address);
      if (test.getScheme() != null)
        return address;
      if (test.getHost() != null && test.getPath() != null)
        return address;
      String specific = test.getSchemeSpecificPart();
      if (specific.indexOf('.') == -1)
        throw new URISyntaxException(specific, "No dot inside domain");
    } catch (URISyntaxException e1) {
      address = "search://" + address;
    }
    return address;
  }

  public void setAddress(CefBrowser browser, String address) {
    if (browser == browser_)
      address_field_.setText(address);
  }
}
