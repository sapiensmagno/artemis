(function() {
    'use strict';

    angular
        .module('artemisApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('delivery', {
            parent: 'entity',
            url: '/delivery',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'artemisApp.delivery.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/delivery/deliveries.html',
                    controller: 'DeliveryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('delivery');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('delivery-detail', {
            parent: 'delivery',
            url: '/delivery/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'artemisApp.delivery.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/delivery/delivery-detail.html',
                    controller: 'DeliveryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('delivery');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Delivery', function($stateParams, Delivery) {
                    return Delivery.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'delivery',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('delivery-detail.edit', {
            parent: 'delivery-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/delivery/delivery-dialog.html',
                    controller: 'DeliveryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Delivery', function(Delivery) {
                            return Delivery.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('delivery.new', {
            parent: 'delivery',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/delivery/delivery-dialog.html',
                    controller: 'DeliveryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('delivery', null, { reload: 'delivery' });
                }, function() {
                    $state.go('delivery');
                });
            }]
        })
        .state('delivery.edit', {
            parent: 'delivery',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/delivery/delivery-dialog.html',
                    controller: 'DeliveryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Delivery', function(Delivery) {
                            return Delivery.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('delivery', null, { reload: 'delivery' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('delivery.delete', {
            parent: 'delivery',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/delivery/delivery-delete-dialog.html',
                    controller: 'DeliveryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Delivery', function(Delivery) {
                            return Delivery.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('delivery', null, { reload: 'delivery' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
