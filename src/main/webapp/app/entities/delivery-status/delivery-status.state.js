(function() {
    'use strict';

    angular
        .module('artemisApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('delivery-status', {
            parent: 'entity',
            url: '/delivery-status',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'artemisApp.deliveryStatus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/delivery-status/delivery-statuses.html',
                    controller: 'DeliveryStatusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('deliveryStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('delivery-status-detail', {
            parent: 'delivery-status',
            url: '/delivery-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'artemisApp.deliveryStatus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/delivery-status/delivery-status-detail.html',
                    controller: 'DeliveryStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('deliveryStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DeliveryStatus', function($stateParams, DeliveryStatus) {
                    return DeliveryStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'delivery-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('delivery-status-detail.edit', {
            parent: 'delivery-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/delivery-status/delivery-status-dialog.html',
                    controller: 'DeliveryStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DeliveryStatus', function(DeliveryStatus) {
                            return DeliveryStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('delivery-status.new', {
            parent: 'delivery-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/delivery-status/delivery-status-dialog.html',
                    controller: 'DeliveryStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                lastUpdate: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('delivery-status', null, { reload: 'delivery-status' });
                }, function() {
                    $state.go('delivery-status');
                });
            }]
        })
        .state('delivery-status.edit', {
            parent: 'delivery-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/delivery-status/delivery-status-dialog.html',
                    controller: 'DeliveryStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DeliveryStatus', function(DeliveryStatus) {
                            return DeliveryStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('delivery-status', null, { reload: 'delivery-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('delivery-status.delete', {
            parent: 'delivery-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/delivery-status/delivery-status-delete-dialog.html',
                    controller: 'DeliveryStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DeliveryStatus', function(DeliveryStatus) {
                            return DeliveryStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('delivery-status', null, { reload: 'delivery-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
