import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPageFollowingListComponent } from './user-page-following-list.component';

describe('UserPageFollowingListComponent', () => {
  let component: UserPageFollowingListComponent;
  let fixture: ComponentFixture<UserPageFollowingListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserPageFollowingListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserPageFollowingListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
