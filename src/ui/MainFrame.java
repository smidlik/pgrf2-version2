package ui;

import model.*;
import raster.Visibility;
import render.Renderer;
import transforms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.function.Function;


public class MainFrame extends JFrame {
    private static final int width = 800;
    private static final int height = 600;
    private static final int sensitivity = 2500;
    private static int FPS = 1000 / 30;
    private final Function<Vertex, Col> shader;
    private JPanel drawPane;
    private JPanel controls;
    private JButton btnDrawMode;
    private JButton btnPresp;
    private JButton btnCubics;
    private JButton btnHelp;
    private JLabel lblInfo;
    private JLabel lblShow;
    private Mat4PerspRH projPrespective;
    private Mat4OrthoRH projOrthhogonal;
    private Mat4 projMat;
    private Mat4 model;
    private boolean isFilled;
    private boolean isCubic;
    private boolean isPresp;
    private boolean animation;
    private String info;
    private Camera camera;
    private double moveX, moveY, moveZ;
    private double speed;
    private int beginX;
    private int beginY;
    private Visibility visibility;
    private BufferedImage img;
    private Renderer renderer;

    public MainFrame() {
        //shader = (vertex)->vertex.getColor().mul(1 / vertex.getPosition().getW());
        shader = (vertex) -> vertex.getColor().mul(4);
        visibility = new Visibility(width, height);
        img = visibility.getBufferedImage();
        renderer = new Renderer(visibility, shader);

        setTitle("PGRF2");
        setSize(width + 200, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        init();
    }

    private void init() {
        camera = new Camera();
        Visibility visibility = new Visibility(width, height);

        //Projekční matice
        projPrespective = new Mat4PerspRH(Math.PI / 4, 1, 0.01, 45);
        projOrthhogonal = new Mat4OrthoRH(10, 10, 0.1, 230);
        projMat = projPrespective;
        info = "Prespektiva";
        isFilled = true;
        isCubic = false;
        animation = false;
        isPresp = true;

        //modelovací matice jednotková
        model = new Mat4Identity();


        initControls();
    }

    private void initControls() {
        Container pane = this.getContentPane();
        pane.setLayout(new BorderLayout());
        drawPane = new JPanel() {

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                setGraphics(g);
            }
        };
        controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.PAGE_AXIS));
        lblInfo = new JLabel();
        lblShow = new JLabel("Controls");
        btnDrawMode = new JButton("WIRE/FILL");
        btnDrawMode.setFocusable(false);
        btnPresp = new JButton("ORT/PRE");
        btnPresp.setFocusable(false);
        btnCubics = new JButton("Cubics Surface");
        btnCubics.setFocusable(false);
        btnHelp = new JButton("Help");
        btnHelp.setFocusable(false);
        controls.add(lblShow);
        controls.add(btnDrawMode);
        controls.add(btnPresp);
        controls.add(btnCubics);
        controls.add(btnHelp);
        pane.add(lblInfo, BorderLayout.NORTH);
        pane.add(drawPane, BorderLayout.CENTER);
        pane.add(controls, BorderLayout.LINE_START);

        btnDrawMode.addActionListener(e -> {
            isFilled = !isFilled;
            draw();
        });
        btnPresp.addActionListener(e -> {
            if (isPresp) {
                projMat = projOrthhogonal;
                info = "Orthogonal";
            } else {
                projMat = projPrespective;
                info = "Prespektiva";
            }
            isPresp = !isPresp;
            draw();
        });
        btnCubics.addActionListener(e -> {
            if (isCubic) {
                initObjects();
            } else {
                initSurface();
            }
            isCubic = !isCubic;
        });
        btnHelp.addActionListener(e -> {
            //showHelp = !showHelp;
            new HeplFrame();
        });

        enableEvents(AWTEvent.KEY_EVENT_MASK);


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                beginX = e.getX();
                beginY = e.getY();
                super.mousePressed(e);
                drawPane.repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                camera = camera.addAzimuth((Math.PI / sensitivity) * (beginX - e.getX()));
                camera = camera.addZenith((Math.PI / sensitivity) * (beginY - e.getY()));
                beginX = e.getX();
                beginY = e.getY();
                draw();
            }
        });
        drawPane.requestFocus();
        drawPane.requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //System.out.println(e.getKeyCode());
                if (e.isShiftDown())
                    speed = 0.3;
                else
                    speed = 0.1;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        camera = camera.forward(speed);
                        break;
                    case KeyEvent.VK_DOWN:
                        camera = camera.backward(speed);
                        break;
                    case KeyEvent.VK_LEFT:
                        camera = camera.left(speed);
                        break;
                    case KeyEvent.VK_RIGHT:
                        camera = camera.right(speed);
                        break;
                    case KeyEvent.VK_SPACE:
                        if (e.isControlDown())
                            camera = camera.down(speed);
                        else
                            camera = camera.up(speed);
                        break;

                    case KeyEvent.VK_W:
                        model = model.mul(new Mat4RotY(Math.PI / 6));
                        break;
                    case KeyEvent.VK_S:
                        model = model.mul(new Mat4RotY(-Math.PI / 6));

                        break;
                    case KeyEvent.VK_A:
                        model = model.mul(new Mat4RotX(Math.PI / 6));

                        break;
                    case KeyEvent.VK_D:
                        model = model.mul(new Mat4RotX(-Math.PI / 6));

                        break;


                    case KeyEvent.VK_I:
                        camera = camera.addZenith(0.05);
                        break;
                    case KeyEvent.VK_J:
                        camera = camera.addAzimuth(0.05);
                        break;
                    case KeyEvent.VK_K:
                        camera = camera.addZenith(-0.05);
                        break;
                    case KeyEvent.VK_L:
                        camera = camera.addAzimuth(-0.05);
                        break;
                    case KeyEvent.VK_R:
                        resetCamera();
                        break;
                    case KeyEvent.VK_1:
                        camera = new Camera(new Vec3D(13, 1, 6), -3, -0.5, 1.0, true);
                        break;
                    case KeyEvent.VK_2:
                        camera = new Camera(new Vec3D(0, 0, 12), -8.8, -1.57, 1.0, true);
                        break;
                    case KeyEvent.VK_3:
                        camera = new Camera(new Vec3D(1, 13, 6), 4.5, -0.5, 1.0, true);
                        break;
                }
                draw();
                super.keyPressed(e);
            }
        });

        drawPane.requestFocus();
        drawPane.requestFocusInWindow();
        initObjects();
    }


    private void draw() {
        lblInfo.setText(info);

        renderer.setFilled(isFilled);
        renderer.setModelMatrix(model);
        renderer.setProjeMatrix(projMat);
        renderer.setViewMatrix(camera.getViewMatrix());

        clear();
        visibility.clear();
        renderer.render();
        drawPane.repaint();

    }

    private void initSurface() {
        renderer.initSolids();
        renderer.addSolid(new Axis());
        renderer.addSolid(new Surface());
        resetCamera();
        draw();
    }

    private void initObjects() {
        renderer.initSolids();
        renderer.addSolid(new Axis());
        //renderer.addSolid(new Surface());
        renderer.addSolid(new Cube());
        renderer.addSolid(new Pyramid());
        resetCamera();
        draw();
    }

    public void clear() {
        Graphics g = img.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    private void resetCamera() {
        moveX = 0;
        moveY = 0;
        moveZ = 0;
        camera = new Camera(new Vec3D(9, 9, 6.4),
                -2.4, -0.57, 1.0, true); //Hotovo
        renderer.setViewMatrix(camera.getViewMatrix());

    }


    public void setGraphics(Graphics g) {
        g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
    }

}