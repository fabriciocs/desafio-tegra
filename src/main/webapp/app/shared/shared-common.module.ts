import { NgModule } from '@angular/core';

import { TesteSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [TesteSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [TesteSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class TesteSharedCommonModule {}
