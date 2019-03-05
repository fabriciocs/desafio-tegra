import { NgModule } from '@angular/core';

import { JhiAlertComponent, JhiAlertErrorComponent, TesteSharedLibsModule } from './';

@NgModule({
    imports: [TesteSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [TesteSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class TesteSharedCommonModule {}
