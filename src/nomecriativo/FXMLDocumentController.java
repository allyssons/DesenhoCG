/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomecriativo;

import java.awt.MouseInfo;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import static java.lang.Math.sqrt;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
//import static nomecriativo.FXMLDocumentController.Regulares;

/**
 *
 * @author allysson.sanciani
 */
public class FXMLDocumentController implements Initializable {

    public Point startDrag, endDrag;
    public static String selectShap = "";
    public static java.awt.Color selectColor = java.awt.Color.black;
    public static List<Poligono> poligonosDesenhados = new ArrayList<>();
    @FXML
    private Canvas chico;
    Poligono pol;

    double x1, y1, x2, y2;
    int cliquesNoChico;

    GraphicsContext gc;
    @FXML
    private AnchorPane panel;
    @FXML
    private Button btnIrreg;
    @FXML
    private Button btnRect;
    @FXML
    private Button btnSalvar;

    /*@FXML
    private Button btnRect;
    @FXML
    private AnchorPane panel;
    @FXML
    private Button btnIrreg;*/
    @FXML
    private void PoligIrre(ActionEvent event) {
        chico.setOnMouseClicked(this::PoligonosIrregulares);
    }

    @FXML
    private void PolegRect(ActionEvent event) {
        chico.setOnMouseClicked(this::retangulo);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        pol = new Poligono();
        gc = chico.getGraphicsContext2D();
        gc.setStroke(Color.BLUE);
        chico.setOnMouseClicked(this::PoligonosIrregulares);
        //   chico.setOnMouseClicked(this::retangulo);

        /*btnIrreg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectShap = "Irregular";
                chico.getParent().setOnKeyPressed((KeyEvent e) -> {
                    System.out.println("noooooooooo");
                });

                cliquesNoChico = 0;

                gc = chico.getGraphicsContext2D();

                gc.setStroke(Color.BLACK);
            }
        });*/

 /*btnRect.setOnAction(new EventHandler<ActionEvent>() {

            @Override

            public void handle(ActionEvent event) {
                selectShap = "Rectangle";
                chico.getParent().setOnKeyPressed((KeyEvent e) -> {
                    System.out.println("fechou");
                }
                );
                gc = chico.getGraphicsContext2D();

                gc.setStroke(Color.BLUE);
                ///gc.strokeRect(30, 30, 40, 40);
                
            }
        });*/
    }

    public void carregou() {
        chico.getScene().setOnKeyPressed((KeyEvent e) -> {
            gc.clearRect(0, 0, chico.getWidth(), chico.getHeight());
            cliquesNoChico = 0;
            pol.pontos.clear();
            // pol.draw(gc);
        });
    }

    private void retangulo(MouseEvent e) {
        System.out.println("eu entrei qui");
        int R = 100, N = 3;
        Point pr = new Point();
        pr = MouseInfo.getPointerInfo().getLocation();
        Ponto prr = new Ponto();
        prr.x = e.getX();
        prr.y = e.getY();

        double grau = (360 / N);
        double xtemp, ytemp;
        for (int i = 0; i < N; i++) {
            Ponto p = new Ponto();
            xtemp = (R * Math.cos((2 * Math.PI * (i)) / N + grau) + prr.x);
            ytemp = (R * Math.sin((2 * Math.PI * (i)) / N + grau) + prr.y);
            p.x = xtemp;
            p.y = ytemp;
            pol.pontos.add(p);
            System.out.println(p);
        }
        // pontos.add(N, pontos.get(0));//fechou poligono
        pol.pontos.add(pol.pontos.get(0));
        poligonosDesenhados.add(pol);

        for (Poligono p : poligonosDesenhados) {
            for (Ponto po : p.pontos) {
                System.out.println("Pontos  :" + po);
            }
        }

        pol.draw(gc);
        pol = new Poligono();
    }   
    
    private void PoligonosIrregulares(MouseEvent e) {
        cliquesNoChico++;

        Point pp = MouseInfo.getPointerInfo().getLocation();
        System.out.println(pp);

        Ponto p = new Ponto();

        p.x = e.getX();
        p.y = e.getY();

        pol.pontos.add(p);

        if (cliquesNoChico >= 3) {
            Ponto p1, p2;
            int i;
            p2 = pol.pontos.get(pol.pontos.size() - 1);
            p1 = pol.pontos.get(0);
            double d;
            d = sqrt(((p2.x - p1.x) * (p2.x - p1.x)) + ((p2.y - p1.y) * (p2.y - p1.y)));
            if (d <= 10) {
                pol.pontos.add(pol.pontos.get(0));
                cliquesNoChico = 0;
                //irregulares
                //salvar o objeto 
                //zerar o cliqueNoChico

                pol.draw(gc);

                poligonosDesenhados.add(pol);
//                for (int j = 0; j <= 10; j++) {
//                    System.out.println("irregulares" + Irregulares.get(pol.(j)));
//                }
                pol = new Poligono();
            }
            System.out.println("saas" + d);
        }

        pol.draw(gc);

    }
    
    private void load(File file){
    }
    
    private void loadText(ArrayList<Poligono> poligonos) {
      /*  Poligono pol;
        for(int i = 0; i < poligonos.size(); i++){
            pol = poligonos[i];
        }*/
        for(Poligono pol : poligonos){
            pol.draw(gc);
        }
    }
    
    private void mouseReleased(MouseEvent e) {
        Point p = new Point();
        p = MouseInfo.getPointerInfo().getLocation();

    }

    public void save(String fileName) {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileOutputStream(fileName));
             for (Poligono p : poligonosDesenhados)
                pw.println(p.pontos);
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @FXML
    private void Saveall(ActionEvent event) {
        save("lista.txt");
    }

}
