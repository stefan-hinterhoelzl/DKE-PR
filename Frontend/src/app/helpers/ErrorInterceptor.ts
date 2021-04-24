import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../services/AuthService';
import { AlertService } from '../services/alertService';



@Injectable({ providedIn: 'root' })
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthService, private alertservice: AlertService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(err => {
            if (err.status === 401) {
                // Logout, if the status 401 is received
                
                this.authenticationService.logout();
                this.alertservice.error("Die Sitzung ist abgelaufen");
            }
            
            //const error = err.error.message || err.statusText;
            return throwError(err);
        }))
    }
}