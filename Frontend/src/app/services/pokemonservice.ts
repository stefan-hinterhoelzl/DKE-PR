import { HttpClient} from "@angular/common/http";
import { Injectable } from "@angular/core";


const pokeAPI = "https://pokeapi.co/api/v2/pokemon-species/";

@Injectable({ providedIn: 'root' })
export class PokemonService
 {

    constructor (private http: HttpClient) {}


    public getRequest(id: String) {
        return this.http.get(pokeAPI+id);
    }


 }