import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from "@angular/router";
import { map, take, tap } from "rxjs/operators";
import { AuthService } from "./AuthService";


@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
    constructor(private router: Router, private authService: AuthService) {
        
    }
    
    canActivate(childRouter: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
            this.authService.autoLogin();
            return this.authService.user.pipe(take(1), map(user => {
                const isAuth = !!user;
                if (isAuth) {
                    return true;
                } else {
                    if(state.url != "/") return this.router.createUrlTree(['/login'], {queryParams:{'redirectURL': state.url}});
                    else return this.router.createUrlTree(['login']);
                }
            }));
        }
    }
    
