import { HttpClient } from '@angular/common/http';
import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/User';
import { AuthService } from '../services/AuthService';
import { PokemonService } from '../services/pokemonservice';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  user: User;
  pokemondata: any;
  selfprofile: boolean;

  constructor(private http: HttpClient, private auth: AuthService, private route: ActivatedRoute, private poke: PokemonService, public router: Router) { }

  ngOnInit(): void {
    let user = localStorage.getItem('user');
    this.user = {...JSON.parse(user)};

    //subscription to the active route
    this.route.params.subscribe(params => {
      let id: string = params['id'];
      this.initialize(id);
    });

  }

  async initialize(id) {
    //reset the user to the active User
    this.user = {...JSON.parse(localStorage.getItem("user"))};
    if (id != this.user.id) {
      this.auth.getUserPerID(id).subscribe((data: User) => {
        this.user = {...data};
        this.selfprofile = false;
        this.getPokemonData();
      });
    } else {
      this.selfprofile = true;
      this.getPokemonData();
    }    
  }

  changePokemon() {
    let newpokemon = this.user.pokemonid;
      while (newpokemon == this.user.pokemonid) {
      newpokemon = (Math.floor(Math.random() * 494)+1).toString();
      }
    this.user.pokemonid = newpokemon;
    //Change the pokemonid in the localstorage
    localStorage.setItem('user', JSON.stringify(this.user));

    this.getPokemonData();
  }

  getPokemonData() {
    //Erst mal nachsehen ob es gecached ist
    if (localStorage.getItem("Pokemondata") && JSON.parse(localStorage.getItem("Pokemondata")).id == this.user.pokemonid && this.selfprofile) {
      this.pokemondata = {...JSON.parse(localStorage.getItem("Pokemondata"))};
      console.log(this.pokemondata);
      //Wenn nicht danh holen und gleich cachen wenn im self profile
    } else {
      var data;
      var name;
      //Get Pokemon Data
      this.poke.getRequest(this.user.pokemonid).subscribe((payload: any) => {
        data = {...payload};
        data.names.forEach(element => {
          if (element.language.name == "de") {
            name = element.name;
          }
        });
        this.pokemondata = {id : this.user.pokemonid, name : name}; 

        if (this.selfprofile) {
          //save data to localstorage
          localStorage.setItem("Pokemondata", JSON.stringify(this.pokemondata));
  
          //update the User on the database
          this.updateUser(this.user, this.user.id);
        }
      });
    }
  }

  updateUser(user: User, id: number) {
    this.auth.updateUser(user, id).subscribe((data: User) => {
      console.log(data)
    });

  }
}
