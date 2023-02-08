package portunus.model;

import portunus.core.IEntry;

/**
 * A basic entry in a Portunus model.
 */
public class Entry implements IEntry {

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public boolean matchesSearch(String searchRequest) {
        if (searchRequest == null || getTitle() == null) {
            return false;
        }
        String lowerCaseTitle = getTitle().toLowerCase();
        String lowerCaseRequest = searchRequest.toLowerCase();
        return lowerCaseTitle.contains(lowerCaseRequest);
    }

}
