(function() {
    'use strict';

    angular
        .module('artemisApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('price-rule', {
            parent: 'entity',
            url: '/price-rule',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'artemisApp.priceRule.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/price-rule/price-rules.html',
                    controller: 'PriceRuleController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('priceRule');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('price-rule-detail', {
            parent: 'price-rule',
            url: '/price-rule/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'artemisApp.priceRule.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/price-rule/price-rule-detail.html',
                    controller: 'PriceRuleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('priceRule');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PriceRule', function($stateParams, PriceRule) {
                    return PriceRule.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'price-rule',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('price-rule-detail.edit', {
            parent: 'price-rule-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/price-rule/price-rule-dialog.html',
                    controller: 'PriceRuleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PriceRule', function(PriceRule) {
                            return PriceRule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('price-rule.new', {
            parent: 'price-rule',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/price-rule/price-rule-dialog.html',
                    controller: 'PriceRuleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('price-rule', null, { reload: 'price-rule' });
                }, function() {
                    $state.go('price-rule');
                });
            }]
        })
        .state('price-rule.edit', {
            parent: 'price-rule',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/price-rule/price-rule-dialog.html',
                    controller: 'PriceRuleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PriceRule', function(PriceRule) {
                            return PriceRule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('price-rule', null, { reload: 'price-rule' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('price-rule.delete', {
            parent: 'price-rule',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/price-rule/price-rule-delete-dialog.html',
                    controller: 'PriceRuleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PriceRule', function(PriceRule) {
                            return PriceRule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('price-rule', null, { reload: 'price-rule' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
