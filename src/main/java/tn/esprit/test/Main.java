package tn.esprit.test;

import tn.esprit.models.Document;
import tn.esprit.services.ServiceDocument;

import java.util.Date;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ServiceDocument serviceDocument = new ServiceDocument();
        Random random = new Random();

        String[] titles = {"Document A", "Document B", "Document C", "Document D", "Document E", "Document F", "Document G", "Document H", "Document I", "Document J", "Document K", "Document L", "Document M", "Document N", "Document O", "Document P", "Document Q", "Document R", "Document S", "Document T"};
        String[] statuses = {"Draft", "Published", "Archived"};
        String[] descriptions = {"Description 1", "Description 2", "Description 3", "Description 4", "Description 5", "Description 6", "Description 7", "Description 8", "Description 9", "Description 10", "Description 11", "Description 12", "Description 13", "Description 14", "Description 15", "Description 16", "Description 17", "Description 18", "Description 19", "Description 20"};
        String[] types = {"Type 1", "Type 2", "Type 3"};
        String[] paths = {"Path 1", "Path 2", "Path 3", "Path 4", "Path 5", "Path 6", "Path 7", "Path 8", "Path 9", "Path 10", "Path 11", "Path 12", "Path 13", "Path 14", "Path 15", "Path 16", "Path 17", "Path 18", "Path 19", "Path 20"};
        String[] categories = {"Categorie1", "Categorie2", "Categorie3"};
        String[] authors = {"Author 1", "Author 2", "Author 3", "Author 4", "Author 5", "Author 6", "Author 7", "Author 8", "Author 9", "Author 10", "Author 11", "Author 12", "Author 13", "Author 14", "Author 15", "Author 16", "Author 17", "Author 18", "Author 19", "Author 20"};

        for (int i = 0; i < 20; i++) {
            Document document = new Document(
                    titles[i],
                    statuses[random.nextInt(statuses.length)],
                    descriptions[i],
                    types[random.nextInt(types.length)],
                    new Date(),
                    new Date(),
                    paths[i],
                    categories[random.nextInt(categories.length)],
                    authors[i]
            );
            serviceDocument.add(document);
        }

        System.out.println("Database populated");
    }
}
