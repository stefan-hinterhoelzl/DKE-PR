import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TooltipModule } from 'ngx-bootstrap/tooltip';

const bootstrapModules = [
    ModalModule,
    BsDropdownModule,
    TooltipModule
];

@NgModule({
    imports: [
      CommonModule,
      ...bootstrapModules,
    ],
    exports: [
      ...bootstrapModules
    ],
  })
  
  export class BootstrapModule { }