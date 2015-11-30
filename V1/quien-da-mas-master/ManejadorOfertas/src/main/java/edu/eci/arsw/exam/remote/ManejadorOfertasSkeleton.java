/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.exam.remote;

import edu.eci.arsw.exam.FachadaPersistenciaOfertas;
import org.springframework.amqp.core.MessageListener;

/**
 *
 * @author hcadavid
 */
public class ManejadorOfertasSkeleton implements ManejadorOfertasStub{

    private FachadaPersistenciaOfertas fpers=null;

    public void setFachadaPersistenciaOfertas(FachadaPersistenciaOfertas fpers) {
        this.fpers = fpers;
    }
            
    @Override
    public void agregarOferta(String codOferente,String codprod,int monto) {
        
        if (!fpers.getMapaOfertasRecibidas().containsKey(codprod)){
            //se ha recibido la primera oferta 
            fpers.getMapaOfertasRecibidas().put(codprod, 1);
            //se asigna el monto propuesto como mejor oferta
            fpers.getMapaMontosAsignados().put(codprod, monto);
            //se asigna al oferente como ganador provisional
            fpers.getMapaOferentesAsignados().put(codprod, codOferente);
        }
        else{
            int ofertasActuales=fpers.getMapaOfertasRecibidas().get(codprod);
            fpers.getMapaOfertasRecibidas().put(codprod,ofertasActuales+1);
            if (fpers.getMapaMontosAsignados().get(codprod)>monto){
                fpers.getMapaMontosAsignados().put(codprod, monto);
                fpers.getMapaOferentesAsignados().put(codprod, codOferente);
            }
        }
        
    }
    
    
    
}
