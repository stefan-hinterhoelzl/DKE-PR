import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { AlertService } from '../services/alertService';



@Component({ selector: 'alert',
             templateUrl: 'alert.component.html' 
            })
export class AlertComponent implements OnInit, OnDestroy {
    private subscription: Subscription;
    message: any;

    constructor(private alertService: AlertService) { }

    ngOnInit() {
        this.subscription = this.alertService.getAlert()
            .subscribe(message => {
                switch (message && message.type) {
                    case 'success':
                        message.cssClass = 'alert-success';
                        break;
                    case 'error':
                        message.cssClass = 'alert-danger';
                        break;
                }

                this.message = message;
                console.log(this.message)
            });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}