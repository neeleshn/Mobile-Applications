//
//  BeaconContentFactory.h
//  BeacontestE7Q
//

#import "BeaconID.h"

@protocol BeaconContentFactory <NSObject>

- (void)contentForBeaconID:(BeaconID *)beaconID completion:(void (^)(id content))completion;

@end
