import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../services/AuthService';
import { AlertService } from '../services/alertService';
import { EMPTY } from 'rxjs';
import { ActivatedRoute, RouterStateSnapshot } from '@angular/router';




@Injectable({ providedIn: 'root' })
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthService, private alertservice: AlertService, private state: RouterStateSnapshot) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(x=> this.handleError(x)));
    }

    private handleError(err: HttpErrorResponse): Observable<any> {
        //handle 401 and rethrow the rest
        if (err.status === 401) {
                this.authenticationService.logout(this.state.url);
                this.alertservice.error("Die Sitzung ist abgelaufen");
            return of(EMPTY);
        }

        if (err.status === 0) {
            this.alertservice.error("Die Server sind Offline. Bitte Entschuldigen Sie die Unannehmlichkeiten")
            return of(EMPTY);
        }

        return throwError(err);
    }
}