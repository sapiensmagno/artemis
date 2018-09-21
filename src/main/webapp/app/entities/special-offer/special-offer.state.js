(function() {
    'use strict';

    angular
        .module('artemisApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('special-offer', {
            parent: 'entity',
            url: '/special-offer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'artemisApp.specialOffer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/special-offer/special-offers.html',
                    controller: 'SpecialOfferController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('specialOffer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('special-offer-detail', {
            parent: 'special-offer',
            url: '/special-offer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'artemisApp.specialOffer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/special-offer/special-offer-detail.html',
                    controller: 'SpecialOfferDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('specialOffer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SpecialOffer', function($stateParams, SpecialOffer) {
                    return SpecialOffer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'special-offer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('special-offer-detail.edit', {
            parent: 'special-offer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/special-offer/special-offer-dialog.html',
                    controller: 'SpecialOfferDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SpecialOffer', function(SpecialOffer) {
                            return SpecialOffer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('special-offer.new', {
            parent: 'special-offer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/special-offer/special-offer-dialog.html',
                    controller: 'SpecialOfferDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                discountPercent: null,
                                startDate: null,
                                endDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('special-offer', null, { reload: 'special-offer' });
                }, function() {
                    $state.go('special-offer');
                });
            }]
        })
        .state('special-offer.edit', {
            parent: 'special-offer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/special-offer/special-offer-dialog.html',
                    controller: 'SpecialOfferDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SpecialOffer', function(SpecialOffer) {
                            return SpecialOffer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('special-offer', null, { reload: 'special-offer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('special-offer.delete', {
            parent: 'special-offer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/special-offer/special-offer-delete-dialog.html',
                    controller: 'SpecialOfferDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SpecialOffer', function(SpecialOffer) {
                            return SpecialOffer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('special-offer', null, { reload: 'special-offer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
