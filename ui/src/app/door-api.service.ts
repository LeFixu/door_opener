import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

export interface DoorUpdate {
  id: string;
  open: boolean;
}

@Injectable({ providedIn: 'root' })
export class DoorApiService {
  private readonly http = inject(HttpClient);

  updateDoor(update: DoorUpdate): Observable<void> {
    return this.http.put<void>('/door', update);
  }
}
