import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { PartnerService } from '../../../services/partner.service';
import { Partner, Direction, FlowType } from '../../../models/partner.model';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-partner-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatCardModule,
    RouterModule
  ],
  templateUrl: './partner-form.component.html',
  styleUrls: ['./partner-form.component.scss']
})
export class PartnerFormComponent implements OnInit, OnDestroy {
  directions = Object.values(Direction);
  flowTypes = Object.values(FlowType);
  isEdit = false;
  private partnerId!: number | null;
  private destroy$ = new Subject<void>();

  partnerForm = this.fb.group({
    alias: ['', Validators.required],
    type: ['', Validators.required],
    direction: [null as Direction | null, Validators.required],
    application: [''],
    processedFlowType: [null as FlowType | null],
    description: ['', Validators.required]
  });

  constructor(
    private fb: FormBuilder,
    private partnerService: PartnerService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.partnerId = +id;
      this.isEdit = true;
      this.partnerService.getPartnerById(this.partnerId)
        .pipe(takeUntil(this.destroy$))
        .subscribe(partner => {
          this.partnerForm.patchValue(partner);
        });
    } else {
      this.partnerId = null;
    }
  }

  onSubmit() {
    if (this.partnerForm.invalid) return;

    const partner = this.partnerForm.value as Partner;
    const operation = this.isEdit 
      ? this.partnerService.updatePartner(this.partnerId!, partner)
      : this.partnerService.createPartner(partner);

    operation
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => this.router.navigate(['/partners']),
        error: err => console.error('Operation failed', err)
    });
  }

  ngOnDestroy(): void {
    // Unsubscribe from all subscriptions
    this.destroy$.next();
    this.destroy$.complete();
  }
}