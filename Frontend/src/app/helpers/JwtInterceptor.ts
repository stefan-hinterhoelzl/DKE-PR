import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/AuthService';

@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add authorization header with jwt token if available
        let token = this.authenticationService.currentTokenValue;
        console.log(token)
        if (token != "") {
            console.log("adding headers")
            request = request.clone({
                setHeaders: { 
                    'Authorization': `Bearer ${token}`
                }
            });
        }

        return next.handle(request);
    }
}