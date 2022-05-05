package narif.poc.findmydoc.model;

public enum SlotsEn {
    FIRST_SLOT("9 am - 12 noon"), SECOND_SLOT("1 pm - 4 pm"), THIRD_SLOT("5 pm - 8 pm");

    SlotsEn(String slots){
        this.slots = slots;
    }

    final String slots;

    public String getSlots() {
        return slots;
    }
}
