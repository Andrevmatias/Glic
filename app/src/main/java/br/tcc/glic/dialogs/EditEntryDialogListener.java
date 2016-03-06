package br.tcc.glic.dialogs;

import br.tcc.glic.domain.core.Registro;

/**
 * Created by André on 06/03/2016.
 */
public interface EditEntryDialogListener {
    void onApply(Registro registro);
    void onDelete(Registro registro);
}
