import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPageNotificationsComponent } from './user-page-notifications.component';

describe('UserPageNotificationsComponent', () => {
  let component: UserPageNotificationsComponent;
  let fixture: ComponentFixture<UserPageNotificationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserPageNotificationsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserPageNotificationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
