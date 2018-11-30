import java.util.HashSet;
import java.util.Set;

class Interrupteur extends CompElec{

    private boolean state;
    private Set<CompElec> composants;
    private boolean power;

    // car le mot switch est réservé à Java
    public void switchInt(){
        this.state = !this.state;
        if(this.state){
            if(this.power)
                for(CompElec c: this.composants)
                    c.power();
        }
    }

    @Override
    void power(){
        super.power();
        if(state)
            for(CompElec c: this.composants)
                c.power();
    }

    @Override
    void unpower(){
        super.unpower();
        if(state)
            for(CompElec c: this.composants)
                c.unpower();
    }

    /**
    * Fusion des méthodes power et switch
    */
    @Override
    void powerSwitch(){
        super.powerSwitch();
        if(state){
            for(CompElec c: compostants)
                c.powerSwitch();
        }
    }
}
