import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPagerFollowersListComponent } from './user-pager-followers-list.component';

describe('UserPagerFollowersListComponent', () => {
  let component: UserPagerFollowersListComponent;
  let fixture: ComponentFixture<UserPagerFollowersListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserPagerFollowersListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserPagerFollowersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
