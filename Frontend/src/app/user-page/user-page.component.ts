import { HttpClient } from '@angular/common/http';
import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/User';
import { AlertService } from '../services/alertService';
import { AuthService } from '../services/AuthService';
import { FollowerService } from '../services/followerservice';
import { PokemonService } from '../services/pokemonservice';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit, OnDestroy {

  user: User;
  pokemondata: any;
  selfprofile: boolean;
  subscription;
  following: number[];
  

  constructor(private http: HttpClient, private auth: AuthService, private route: ActivatedRoute, private poke: PokemonService, public router: Router, private alertservice: AlertService, private fs: FollowerService) { }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.auth.user.subscribe((data: User) => {
      this.user = data;
    });

    this.fs.following.subscribe((data) => {
      this.following = data;
    });

    //subscription to the active route
    this.subscription = this.route.params.subscribe(params => {
      let id: string = params['id'];
      this.initialize(id);
    });

  }

  initialize(id) {
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
      this.setPokemonID(newpokemon);

  }

  setPokemonID(pokemonid: String) {
    let changedUser = Object.assign({}, this.user);

    changedUser.pokemonid = pokemonid;
    
    this.auth.updateUser(changedUser, changedUser.id).subscribe((data: User) => {
        this.user = data;
        this.auth.user.next(this.user);
        localStorage.setItem('user', JSON.stringify(this.user))
        this.getPokemonData();
    });
  }

  getPokemonData() {
    //Erst mal nachsehen ob es gecached ist
    if (localStorage.getItem("Pokemondata") && JSON.parse(localStorage.getItem("Pokemondata")).id == this.user.pokemonid && this.selfprofile) {
      this.pokemondata = {...JSON.parse(localStorage.getItem("Pokemondata"))};


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
        }
      });
    }
  }

  async followUser() {
    await this.fs.followUser(this.auth.currentUserValue.id, this.user.id).then(() =>{
      this.following.push(this.user.id);
      this.fs.following.next(this.following);
      console.log(this.following)
    });
  }

  async unfollowUser(){
    await this.fs.UnfollowUser(this.auth.currentUserValue.id, this.user.id).then(() =>{
      let i = this.following.indexOf(this.user.id);
      this.following.splice(i, 1);
      this.fs.following.next(this.following);
    });

  }
  
}
