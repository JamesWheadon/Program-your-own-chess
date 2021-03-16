/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessgui;

public class Coord {
    public int x;
    public int y;
    
    public Coord(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public String toString() {
        String xtext = this.x + "";
        String ytext = this.y + "";
        return "(x: " + xtext + " y: " + ytext + ")";
    }
}
