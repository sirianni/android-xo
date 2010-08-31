package com.ericsirianni.xo.controller;

import com.ericsirianni.xo.model.Game;
import com.ericsirianni.xo.model.Square;
import com.google.inject.ImplementedBy;

@ImplementedBy(RandomCPUStrategy.class)
interface CPUStrategy {

    Square selectSquare(Game game);
    
}
