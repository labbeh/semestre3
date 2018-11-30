class CompElec{

    private boolean power;

    void power(){
        power = true;
    }

    void unpower(){
        power = false;
    }

    void powerSwitch(){
        power = !power;
    }
}
