//
//  ViewController.m
//  TestnotificationKb3
//

#import "ViewController.h"
#import "BeaconDetails.h"
#import "BeaconDetailsCloudFactory.h"
#import "CachingContentFactory.h"
#import "ProximityContentManager.h"

@interface ViewController () <ProximityContentManagerDelegate>

@property (weak, nonatomic) IBOutlet UILabel *label;
@property (weak, nonatomic) IBOutlet UIImageView *image;
@property (weak, nonatomic) IBOutlet UIActivityIndicatorView *activityIndicator;

@property (nonatomic) ProximityContentManager *proximityContentManager;

@end

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    [self.activityIndicator startAnimating];
    
    self.proximityContentManager = [[ProximityContentManager alloc]
                                    initWithBeaconIDs:@[
                                                        [[BeaconID alloc] initWithUUIDString:@"XXXX" major:3 minor:4],
                                                        [[BeaconID alloc] initWithUUIDString:@"XXXX" major:3 minor:4],
                                                        [[BeaconID alloc] initWithUUIDString:@"XXXX" major:3 minor:4]
                                                        ]
                                    beaconContentFactory:[[CachingContentFactory alloc] initWithBeaconContentFactory:[BeaconDetailsCloudFactory new]]];
    
    self.proximityContentManager.delegate = self;
    [self.proximityContentManager startContentUpdates];
    // Do any additional setup after loading the view, typically from a nib.
}

- (void)proximityContentManager:(ProximityContentManager *)proximityContentManager didUpdateContent:(id)content {
    [self.activityIndicator stopAnimating];
    [self.activityIndicator removeFromSuperview];
    
    
    BeaconDetails *beaconDetails = content;
    if ([beaconDetails.beaconName isEqual:@"AtsMint"]) {
        self.view.backgroundColor = beaconDetails.backgroundColor;
        self.label.text = @"You're in Kick Off Room.\nFollowing are the sessions here:\n - 10:00 am - Commencement\n - 04:00 pm Closing Session";
        self.image.hidden = NO;
    } else if ([beaconDetails.beaconName isEqual:@"AtsIce"]) {
        self.view.backgroundColor = beaconDetails.backgroundColor;
        self.label.text = @"You're in Room 201.\nFollowing are the sessions here:\n - 12:30 pm - Tech Talk1\n - 2:00pm Tech Talk2";
        self.image.hidden = NO;
    } else if ([beaconDetails.beaconName isEqual:@"AtsBlue"]) {
        self.view.backgroundColor = beaconDetails.backgroundColor;
        self.label.text = @"You're in Room 202.\nFollowing are the sessions here:\n - 12:30 pm - Tech Talk1\n - 2:00pm Tech Talk2";
        self.image.hidden = NO;
    } else {
        self.view.backgroundColor = BeaconDetails.neutralColor;
        self.label.text = @"No beacons in range.";
        self.image.hidden = YES;
    }
}

- (UIStatusBarStyle)preferredStatusBarStyle {
    return UIStatusBarStyleLightContent;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
