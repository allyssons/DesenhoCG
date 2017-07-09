package nomecriativo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;


class Poligono implements Serializable{

    public List<Ponto> pontos = new ArrayList<>();

    public void draw(GraphicsContext c) {
        Ponto p = null;

        for (Ponto ponto : pontos) {      //pega cada ponto da lista de pontos
            if (p == null) {
                p = ponto;
                continue;
            }

            c.strokeLine(p.x, p.y, ponto.x, ponto.y);
            p = ponto;
        }
    }
}