import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { take } from "rxjs/operators";
import { User } from "../model/User";
 
 const searchServiceAPIURL: String = "http://localhost:8086/" 
 
@Injectable({ providedIn: 'root' })
export class SearchService
 {

    constructor(private http: HttpClient) {}



    getUsers(searchterm: string): Observable<User[]> {
        return this.http.get<User[]>(searchServiceAPIURL+"users/"+searchterm).pipe(take(1));
    }


 }