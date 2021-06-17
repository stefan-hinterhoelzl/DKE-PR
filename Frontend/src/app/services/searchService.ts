import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { take } from "rxjs/operators";
import { Posting } from "../model/Posting";
import { User } from "../model/User";
 
 const searchServiceAPIURL: String = "http://localhost:8086/" 
 
@Injectable({ providedIn: 'root' })
export class SearchService
 {

    constructor(private http: HttpClient) {}



    getUsers(searchterm: string): Promise<User[]> {
        return this.http.get<User[]>(searchServiceAPIURL+"users/"+searchterm).toPromise();
    }

    getPosts(searchterm: string): Promise<Posting[]> {
        return this.http.get<Posting[]>(searchServiceAPIURL+"posts/"+searchterm).toPromise();
    }


 }