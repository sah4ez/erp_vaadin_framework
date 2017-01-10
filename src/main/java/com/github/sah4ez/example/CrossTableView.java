package com.github.sah4ez.example;

import com.github.sah4ez.core.elements.CommonView;
import com.github.sah4ez.core.permission.ModifierAccess;
import com.github.sah4ez.example.layout.CrossTableLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aleksandr on 08.01.17.
 */
public class CrossTableView extends CommonView{
    private MyLogic logic = new MyLogic(this);

    public CrossTableView(){
        setLogic(logic);
        CrossTableLayout layout = new CrossTableLayout(logic, "crosstable1");
        layout.createData("id", "name", "id", "name");
        layout.getTable().setColumnCollapsed("id", true);
        addComponent(layout);
        setSizeFull();
    }
    @Override
    public Map<String, ModifierAccess> loadComponents() {
        Map<String, ModifierAccess> map = new HashMap<>();
        return map;
    }
}
