/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.view.model;

import hr.algebra.model.Actor;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Branimir
 */
public class ActorTableModel extends AbstractTableModel{
    
    private static final String[] COLUMN_NAMES = {"ID", "First Name", "Last Name"};
    
    private List<Actor> actors;
    
    public ActorTableModel(List<Actor> actors) {
        this.actors = actors;
    }
    
    public void setActors(List<Actor> actors) {
        this.actors = actors;
        fireTableDataChanged();
    }
    
    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }
    
    @Override
    public int getRowCount() {
        return actors.size();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Actor actor = actors.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> actor.getIdActor();
            case 1 -> actor.getFirstName();
            case 2 -> actor.getLastName();
            // lose:
            default -> null;
        };
    }
    
    @Override 
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 ? Integer.class : String.class;
    }
}
