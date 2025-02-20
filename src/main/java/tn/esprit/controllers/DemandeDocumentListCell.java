package tn.esprit.controllers;

import javafx.scene.control.ListCell;
import tn.esprit.models.DemandeDocument;

public class DemandeDocumentListCell extends ListCell<DemandeDocument> {
    @Override
    protected void updateItem(DemandeDocument item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
        } else {
            setText(item.getTitreDemande() + " - " + item.getStatus());
        }
    }
}